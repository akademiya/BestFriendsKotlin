package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdView


class InfoView : MainActivity() {
    private val rater = object : AppRater(){}
    private lateinit var privacyPolicy: TextView
    private lateinit var site: Button
    private lateinit var version: TextView
    private val storage = FirebaseStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_info)

        privacyPolicy = findViewById(R.id.private_policy)
        site = findViewById(R.id.site)
        version = findViewById(R.id.version)
        val adContainer: AdView = findViewById(R.id.adView)
        val infoMessage = findViewById<TextView>(R.id.info_message)

        storage.infoMessageFromFB { message ->
            if (message.isNullOrEmpty()) {
                infoMessage.visibility = View.GONE
            } else {
                infoMessage.visibility = View.VISIBLE
                infoMessage.text = message
            }
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        privacyPolicy.movementMethod = LinkMovementMethod.getInstance()

        site.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.site_link)))
            browserIntent.noAnimation()
            startActivity(browserIntent)
        }

        rater.appLaunched(this)

        val currentVersion: String = "v. " + packageManager.getPackageInfo(packageName, 0).versionName
        version.text = currentVersion


        if (isNetworkAvailable()) {
            adContainer.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.ko -> setAppLanguage("ko")
            R.id.en -> setAppLanguage("en")
            R.id.ua -> setAppLanguage("uk")
            R.id.fr -> setAppLanguage("fr")
            R.id.ru -> setAppLanguage("ru")
            R.id.light -> setDarkMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.dark -> setDarkMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAppLanguage(languageCode: String) {
        setLocale(this, languageCode)
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setDarkMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        DarkModePreferences(this).darkMode = if (mode == AppCompatDelegate.MODE_NIGHT_YES) 1 else 0
        delegate.applyDayNight()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(MainActivity.EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }


}