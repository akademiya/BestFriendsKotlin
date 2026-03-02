package com.vadym.gvd.bestfriendskotlin.traditions

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
//        val adDivider: View = findViewById(R.id.adDivider)

        toolbarButtonMenu()

        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
//            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
//            adDivider.visibility = View.GONE
        }

//        val traditions = List<TraditionItem>(
//            getString(R.string.sunday_service),
//            getString(R.string.hdh),
//            getString(R.string.pledge),
//            getString(R.string.anshiil),
//            getString(R.string.salt),
//            getString(R.string.vine),
//            getString(R.string.candle),
//            getString(R.string.clothes),
//            getString(R.string.photo_of_tp),
//            getString(R.string.days8),
//            getString(R.string.birthday),
//            getString(R.string.songhwa),
//            getString(R.string.life_service),
//            getString(R.string.desyatyna),
//            getString(R.string.prayer_2g),
//            getString(R.string.prayer_tradition)
//        )

        val traditions = listOf(
            TraditionItem(getString(R.string.sunday_service)),
            TraditionItem(getString(R.string.hdh)),
            TraditionItem(getString(R.string.pledge)),
            TraditionItem(getString(R.string.anshiil)),
            TraditionItem(getString(R.string.salt)),
            TraditionItem(getString(R.string.vine)),
            TraditionItem(getString(R.string.candle)),
            TraditionItem(getString(R.string.clothes)),
            TraditionItem(getString(R.string.photo_of_tp)),
            TraditionItem(getString(R.string.days8)),
            TraditionItem(getString(R.string.birthday)),
            TraditionItem(getString(R.string.songhwa)),
            TraditionItem(getString(R.string.life_service)),
            TraditionItem(getString(R.string.desyatyna)),
            TraditionItem(getString(R.string.prayer_2g)),
            TraditionItem(getString(R.string.prayer_tradition))
        )


        val rvTraditions: RecyclerView = findViewById(R.id.rv_traditions)


        // ─── В Activity / Fragment ────────────────────────────────────────

        rvTraditions.layoutManager = GridLayoutManager(this, 3)
        rvTraditions.adapter = TraditionsAdapter(traditions) { position ->
//            if (position == traditions.lastIndex) {
//                startActivity(Intent(this, ExplanationView::class.java).noAnimation())
//            } else {
//                val clickedTraditionTitle = traditions[position]
//                val intent = Intent(this, GeneralViewContent::class.java)
//                intent.putExtra("TRADITION_NAME", clickedTraditionTitle)
//                intent.putExtra("TRADITION_POSITION", position)
//                startActivity(intent)
//            }

        }


//        val adapter = TraditionsDataAdapter(traditions) { position ->
//            if (position == traditions.lastIndex) {
//                startActivity(Intent(this, ExplanationView::class.java).noAnimation())
//            } else {
//                val clickedTraditionTitle = traditions[position]
//                val intent = Intent(this, GeneralViewContent::class.java)
//                intent.putExtra("TRADITION_NAME", clickedTraditionTitle)
//                intent.putExtra("TRADITION_POSITION", position)
//                startActivity(intent)
//            }
//        }
        rvTraditions.layoutManager = GridLayoutManager(this, 2)
//        rvTraditions.adapter = adapter

        rvTraditions.setOnClickListener {  }
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