package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vadym.gvd.bestfriendskotlin.condition.ConditionView
import com.vadym.gvd.bestfriendskotlin.father_kido.FatherKidoView
import com.vadym.gvd.bestfriendskotlin.kido.PersonView
import java.net.URL
import java.util.Locale


open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocal()
        setContentView(R.layout.activity_main)
        CheckTheme.checkTheme(this, delegate)
        screenOn(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        if (isNetworkAvailable(this)) {
            GetVersionCode(this).execute()
        }


    }

    private fun loadLocal() {
        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language", "") ?: "en"
        setLocale(this, languageCode)
    }

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language", "") ?: "en"
        val localeUpdatedContext = setLocale(newBase, languageCode)
        super.attachBaseContext(localeUpdatedContext)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_share) { return true }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mense -> startActivity(Intent(this, MenseView::class.java).noAnimation())
            R.id.nav_anthem -> startActivity(Intent(this, AnthemView::class.java).noAnimation())
            R.id.nav_kido -> startActivity(Intent(this, PersonView::class.java).noAnimation())
            R.id.nav_father_kido -> startActivity(Intent(this, FatherKidoView::class.java).noAnimation())
            R.id.nav_phrase_day -> startActivity(Intent(this, PhraseForDay::class.java).noAnimation())
            R.id.nav_kido_explanation -> startActivity(Intent(this, ExplanationView::class.java).noAnimation())
            R.id.nav_kido_condition -> startActivity(Intent(this, ConditionView::class.java).noAnimation())
//            R.id.nav_experiences_prayer -> startActivity(Intent(this, ExperiencesPrayerView::class.java).noAnimation())
            R.id.nav_info -> startActivity(Intent(this, InfoView::class.java))
            R.id.nav_facebook -> startActivity(openFacebookIntent(this))
            R.id.nav_share -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                val shareBody = getString(R.string.share_body)
                sharingIntent.apply {
                    noAnimation()
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "True Father Prayers")
                    putExtra(Intent.EXTRA_TEXT, shareBody + URL("https", "play.google.com", "store/apps/details?id=me.vadym.adv.tfprayer"))
                }
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_by)))
            }

            R.id.nav_send -> {
                val uri = Uri.parse("mailto:vadym.adv@gmail.com")
                val sendIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                sendIntent.data = uri
                sendIntent.noAnimation()
                startActivity(Intent.createChooser(sendIntent, "True Father Prayers"))
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    open fun openFacebookIntent(context: Context): Intent? {
        val url = "https://www.facebook.com/groups/tfprayer"
        return try {
            context.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$url"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun setLocale(context: Context, languageCode: String) : Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", languageCode).apply()

        return context.createConfigurationContext(config)
    }



}