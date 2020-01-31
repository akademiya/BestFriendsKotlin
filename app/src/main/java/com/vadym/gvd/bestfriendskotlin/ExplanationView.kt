package com.vadym.gvd.bestfriendskotlin

import android.os.Bundle
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.view_kido_explanation.*

class ExplanationView: MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido_explanation)

        tracker().setScreenName("Kido Explanation")
        tracker().send(HitBuilders.ScreenViewBuilder().build())

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}