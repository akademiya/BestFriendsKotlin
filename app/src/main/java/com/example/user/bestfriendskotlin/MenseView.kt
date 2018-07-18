package com.example.user.bestfriendskotlin

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.google.android.gms.analytics.HitBuilders

class MenseView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kajon_mense)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        // analytics tracker
        tracker().setScreenName("Mense")
        tracker().send(HitBuilders.EventBuilder()
                .setCategory("Mense View")
                .build())
    }
}