package com.vadym.gvd.bestfriendskotlin.traditions

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.ExplanationView
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.noAnimation

class TraditionsView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_traditions)
        val adContainer: AdView = findViewById(R.id.adViewTraditions)
        val adDivider: View = findViewById(R.id.adDivider)

        toolbarButtonMenu()

        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }

        val traditions = listOf(
            getString(R.string.sunday_service),
            getString(R.string.hdh),
            getString(R.string.pledge),
            getString(R.string.anshiil),
            getString(R.string.salt),
            getString(R.string.vine),
            getString(R.string.candle),
            getString(R.string.clothes),
            getString(R.string.photo_of_tp),
            getString(R.string.days8),
            getString(R.string.birthday),
            getString(R.string.songhwa),
            getString(R.string.life_service),
            getString(R.string.desyatyna),
            getString(R.string.prayer_2g),
            getString(R.string.prayer_tradition)
        )

        val gridView: GridView = findViewById(R.id.gv_traditions)
        val gridAdapter = TraditionsDataAdapter(this, traditions)
        gridView.adapter = gridAdapter


        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (position == traditions.lastIndex) {
                startActivity(Intent(this, ExplanationView::class.java).noAnimation())
            } else {
                val clickedTraditionTitle = traditions[position]
                val intent = Intent(this, GeneralViewContent::class.java)
                intent.putExtra("TRADITION_NAME", clickedTraditionTitle)
                intent.putExtra("TRADITION_POSITION", position)
                startActivity(intent)
            }
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