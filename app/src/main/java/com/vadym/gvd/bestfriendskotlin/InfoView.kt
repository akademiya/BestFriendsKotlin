package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.ads.AdView
import com.google.android.material.imageview.ShapeableImageView

class InfoView : MainActivity() {

    private lateinit var privacyPolicy: TextView
    private lateinit var site: ShapeableImageView
    private lateinit var version: TextView
    private val rater by lazy { AppRater(this) }
    private val storage = FirebaseStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_info)

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        bindViews()
        setupToolbar()
        applyDrawerMode(drawerToggle)
        setupListeners()
        loadData()
    }

    private fun bindViews() {
        privacyPolicy = findViewById(R.id.private_policy)
        site = findViewById(R.id.site)
        version = findViewById(R.id.version)
    }

    private fun setupToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupListeners() {
        privacyPolicy.movementMethod = LinkMovementMethod.getInstance()

        site.setOnClickListener {
            val uri = Uri.parse(resources.getString(R.string.site_link))
            Intent(Intent.ACTION_VIEW, uri).apply { noAnimation() }.also { startActivity(it) }
        }
    }

    private fun loadData() {
        val adContainer: AdView = findViewById(R.id.adView)
        val infoMessage = findViewById<TextView>(R.id.info_message)
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Firebase повідомлення
        storage.infoMessageFromFB { message ->
            infoMessage.visibility = if (message.isNullOrEmpty()) View.GONE else View.VISIBLE
            infoMessage.text = message

            if (!message.isNullOrEmpty()) {
                prefs.edit().putString("last_seen_info_message", message).apply()
            }
        }

        // Версія додатку
        runCatching {
            "v. ${packageManager.getPackageInfo(packageName, 0).versionName}"
        }.onSuccess {
            version.text = it
        }

        // Реклама
        if (isNetworkAvailable()) {
            window.decorView.post {
                adContainer.visibility = View.VISIBLE
                Admob.initializeAdmob(this, adContainer)
            }

        } else {
            adContainer.visibility = View.GONE
        }

        // Рейтинг
        rater.appLaunched()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ko -> setAppLanguage("ko")
            R.id.en -> setAppLanguage("en")
            R.id.ua -> setAppLanguage("uk")
            R.id.fr -> setAppLanguage("fr")
            R.id.ru -> setAppLanguage("ru")
            R.id.light -> setDarkMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.dark -> setDarkMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setAppLanguage(languageCode: String) {
        setLocale(this, languageCode)
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { startActivity(it) }
    }

    private fun setDarkMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        DarkModePreferences(this).darkMode = if (mode == AppCompatDelegate.MODE_NIGHT_YES) 1 else 0
        delegate.applyDayNight()
    }

    override fun setContentMargin(marginPx: Int) {
        // Знаходимо кореневий LinearLayout view_info.xml
        val root = findViewById<android.widget.LinearLayout>(R.id.info_root) ?: return
        val lp = root.layoutParams
        if (lp is android.view.ViewGroup.MarginLayoutParams) {
            lp.marginStart = marginPx
            root.layoutParams = lp
        }
    }

    override fun applyDrawerMode(toggle: ActionBarDrawerToggle?) {
        super.applyDrawerMode(toggle)   // drawer lock + setContentMargin

        val isLandscape = resources.configuration.orientation ==
                android.content.res.Configuration.ORIENTATION_LANDSCAPE

        if (!isLandscape) {
            // Тільки у portrait — показуємо Back замість гамбургера
            toggle?.isDrawerIndicatorEnabled = false
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        applyDrawerMode(drawerToggle)
    }

    override fun onBackPressed() {
        val isLandscape = resources.configuration.orientation ==
                android.content.res.Configuration.ORIENTATION_LANDSCAPE

        if (!isLandscape && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}