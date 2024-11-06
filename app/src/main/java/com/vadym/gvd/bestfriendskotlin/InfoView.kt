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
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdView
import com.google.android.gms.analytics.HitBuilders


class InfoView : MainActivity() {
    private val rater = object : AppRater(){}
    private lateinit var privacyPolicy: TextView
    private lateinit var site: Button
    private lateinit var version: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_info)

        privacyPolicy = findViewById(R.id.private_policy)
        site = findViewById(R.id.site)
        version = findViewById(R.id.version)
        val adContainer: AdView = findViewById(R.id.adView)

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


        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.theme_mode, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.light -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                DarkModePreferences(this).darkMode = 0
                delegate.applyDayNight()
            }
            R.id.dark -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                DarkModePreferences(this).darkMode = 1
                delegate.applyDayNight()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}