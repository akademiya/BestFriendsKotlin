package com.example.user.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.method.LinkMovementMethod
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.view_info.*


class InfoView : MainActivity() {
    private val rater = object : AppRater(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_info)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }
        private_policy.movementMethod = LinkMovementMethod.getInstance()

        coffee_gift.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/tfprayer/main"))
            startActivity(browserIntent)
        }

        // analytics tracker
        tracker().setScreenName("Info")
        tracker().send(HitBuilders.EventBuilder()
                .setCategory("Father Kido")
                .setAction("List")
                .build())

        rater.app_launched(this)
    }
}