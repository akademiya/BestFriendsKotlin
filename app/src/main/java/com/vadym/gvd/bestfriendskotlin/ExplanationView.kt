package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView

class ExplanationView: MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido_explanation)
        toolbarButtonMenu()
        setupBackPress()

        val adContainer1: AdView = findViewById(R.id.adView)
        val adContainer2: AdView = findViewById(R.id.adView2)
        val adContainer3: AdView = findViewById(R.id.adView3)
        val adContainer4: AdView = findViewById(R.id.adView4)
        val adContainer5: AdView = findViewById(R.id.adView5)


        if (isNetworkAvailable()) {
            window.decorView.post {
                adContainer1.visibility = View.VISIBLE
                adContainer2.visibility = View.VISIBLE
                adContainer3.visibility = View.VISIBLE
                adContainer4.visibility = View.VISIBLE
                adContainer5.visibility = View.VISIBLE
                Admob.initializeAdmob(this, adContainer1)
                Admob.initializeAdmob(this, adContainer2)
                Admob.initializeAdmob(this, adContainer3)
                Admob.initializeAdmob(this, adContainer4)
                Admob.initializeAdmob(this, adContainer5)
            }
        } else {
            adContainer1.visibility = View.GONE
            adContainer2.visibility = View.GONE
            adContainer3.visibility = View.GONE
            adContainer4.visibility = View.GONE
            adContainer5.visibility = View.GONE
        }
    }

    private fun toolbarButtonMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            navigateBack()
        }
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }

}