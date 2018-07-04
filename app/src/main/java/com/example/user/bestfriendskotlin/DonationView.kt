package com.example.user.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.method.LinkMovementMethod
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.view_info.*


class DonationView : MainActivity() {

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

        donation.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sendmoney.privatbank.ua/ru/?hash=564861218"))
            startActivity(browserIntent)
        }

        // analytics tracker
        tracker().setScreenName("Info")
        tracker().send(HitBuilders.EventBuilder()
                .setCategory("Father Kido")
                .setAction("List")
                .build())
    }
}