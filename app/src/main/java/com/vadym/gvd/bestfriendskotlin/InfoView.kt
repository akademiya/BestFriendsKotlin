package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
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
}