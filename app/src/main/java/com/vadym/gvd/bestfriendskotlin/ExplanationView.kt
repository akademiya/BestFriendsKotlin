package com.vadym.gvd.bestfriendskotlin

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView

class ExplanationView: MainActivity() {

    private val ARTICLE_KEY = "read_Традиції -> Молитва"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido_explanation)
        toolbarButtonMenu()

        val adContainer1: AdView = findViewById(R.id.adView)
        val adContainer2: AdView = findViewById(R.id.adView2)
        val adContainer3: AdView = findViewById(R.id.adView3)
        val adContainer4: AdView = findViewById(R.id.adView4)
        val adContainer5: AdView = findViewById(R.id.adView5)

        AdManager.setupBanner(this, adContainer1)
        AdManager.setupBanner(this, adContainer2)
        AdManager.setupBanner(this, adContainer3)
        AdManager.setupBanner(this, adContainer4)
        AdManager.setupBanner(this, adContainer5)


        val scoin = findViewById<ImageView>(R.id.sj_coin_in_prayer)
        val alreadyRead = getSharedPreferences("articles_read", MODE_PRIVATE)
            .getBoolean(ARTICLE_KEY, false)

        scoin.visibility = if (alreadyRead) View.GONE else View.VISIBLE

        scoin.setOnClickListener {
            // 1. Звук
            val mediaPlayer = MediaPlayer.create(this, R.raw.sj_coin)
            mediaPlayer.setOnCompletionListener { it.release() }
            mediaPlayer.start()

            // 2. Ховаємо монету
            scoin.visibility = View.GONE

            // 3. Зберігаємо факт прочитання → ReadArticleTask.isCompleted() поверне true
            getSharedPreferences("articles_read", MODE_PRIVATE)
                .edit()
                .putBoolean(ARTICLE_KEY, true)
                .apply()
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
            onBackPressed()
        }
    }

}