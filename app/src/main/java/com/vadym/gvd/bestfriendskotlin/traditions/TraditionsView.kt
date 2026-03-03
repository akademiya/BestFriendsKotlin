package com.vadym.gvd.bestfriendskotlin.traditions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.ExplanationView
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.noAnimation

class TraditionsView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_traditions)
        toolbarButtonMenu()

        val traditions = listOf(
            TraditionItem(getString(R.string.sunday_service), R.drawable.sunday_service),
            TraditionItem(getString(R.string.hdh), R.drawable.hdh),
            TraditionItem(getString(R.string.pledge), R.drawable.pledge),
            TraditionItem(getString(R.string.anshiil), R.drawable.anshiil),
            TraditionItem(getString(R.string.photo_of_tp), R.drawable.tp_photo),
            TraditionItem(getString(R.string.days8), R.drawable.day8),
            TraditionItem(getString(R.string.birthday), R.drawable.birthday3),
            TraditionItem(getString(R.string.clothes), R.drawable.holy_clothes3),
            TraditionItem(getString(R.string.life_service), R.drawable.life_service),
            TraditionItem(getString(R.string.songhwa), R.drawable.sonhwa),
            TraditionItem(getString(R.string.prayer_2g), R.drawable.second_gen),
            TraditionItem(getString(R.string.prayer_tradition), R.drawable.tf5),
            TraditionItem(getString(R.string.desyatyna)),
            TraditionItem(getString(R.string.salt)),
            TraditionItem(getString(R.string.vine)),
            TraditionItem(getString(R.string.candle))
        )

        val rvTraditions: RecyclerView = findViewById(R.id.rv_traditions)

        rvTraditions.layoutManager = GridLayoutManager(this, 2)
        rvTraditions.adapter = TraditionsAdapter(traditions) { position ->
            if (position == 11) {
                startActivity(Intent(this, ExplanationView::class.java).noAnimation())
            } else {
                val intent = Intent(this, GeneralViewContent::class.java).apply {
                    putExtra("TRADITION_TITLE", traditions[position].title)
                    putExtra("TRADITION_POSITION", position)
                }
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