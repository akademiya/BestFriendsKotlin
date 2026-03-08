package com.vadym.gvd.bestfriendskotlin.traditions

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.toHtml

class GeneralViewContent : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_general_traditions_content)
        toolbarButtonMenu()
        val adContainer: AdView = findViewById(R.id.adViewTraditionsContent)
        val adDivider: View = findViewById(R.id.adDivider)
        val traditionTitle: TextView = findViewById(R.id.tradition_title)
        val traditionDescription: TextView = findViewById(R.id.tradition_description)
        val traditionsImage: ImageView = findViewById(R.id.traditions_img)


        if (isNetworkAvailable()) {
            window.decorView.post {
                adContainer.visibility = View.VISIBLE
                adDivider.visibility = View.VISIBLE
                Admob.initializeAdmob(this, adContainer)
            }

        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }

        traditionTitle.text = intent.getStringExtra("TRADITION_NAME") ?: "Unknown Tradition"
        val position = intent.getIntExtra("TRADITION_POSITION", 0)
        when(position) {
            0 -> traditionDescription.text = getString(R.string.sunday_service_description)
            1 -> traditionDescription.text = getString(R.string.hdh_description).toHtml()
            2 -> traditionDescription.text = getString(R.string.pledge_description).toHtml()
            3 -> traditionDescription.text = getString(R.string.anshiil_description).toHtml()
            4 -> traditionDescription.text = getString(R.string.photo_of_tp_description)
            5 -> traditionDescription.text = getString(R.string.days8_description).toHtml()
            6 -> traditionDescription.text = getString(R.string.birthday_description).toHtml()
            7 -> traditionDescription.text = getString(R.string.clothes_description)
            8 -> traditionDescription.text = getString(R.string.life_service_description).toHtml()
            9 -> traditionDescription.text = getString(R.string.songhwa_description).toHtml()
            10 -> traditionDescription.text = getString(R.string.prayer_2g_description).toHtml()
            12 -> traditionDescription.text = getString(R.string.desyatyna_description).toHtml()
            13 -> {
                traditionDescription.text = getString(R.string.salt_description).toHtml()
                traditionsImage.setImageResource(R.drawable.tr_salt)
            }
            14 -> {
                traditionDescription.text = getString(R.string.vine_description).toHtml()
                traditionsImage.setImageResource(R.drawable.tr_vine)
            }
            15 -> traditionDescription.text = getString(R.string.candle_description).toHtml()
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
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }
}