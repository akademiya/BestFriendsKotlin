package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.view_info.*


class InfoView : MainActivity() {
    private val rater = object : AppRater(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_info)
        tracker().setScreenName("Info")
        tracker().send(HitBuilders.ScreenViewBuilder().build())

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        private_policy.movementMethod = LinkMovementMethod.getInstance()

        site.setOnClickListener {
            tracker().send(HitBuilders.EventBuilder()
                    .setAction("go-to-site")
                    .setCategory("Info screen")
                    .build())
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.site_link)))
            browserIntent.noAnimation()
            startActivity(browserIntent)
        }

        rater.appLaunched(this)
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