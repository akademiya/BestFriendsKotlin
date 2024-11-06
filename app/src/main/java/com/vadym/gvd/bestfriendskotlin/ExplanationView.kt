package com.vadym.gvd.bestfriendskotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView

class ExplanationView: MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido_explanation)

        val adContainer: AdView = findViewById(R.id.adView)
        val adContainer2: AdView = findViewById(R.id.adView2)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }


        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
            adContainer2.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
            Admob.initializeAdmob(this, adContainer2)
        } else {
            adContainer.visibility = View.GONE
            adContainer2.visibility = View.GONE
        }
    }
}