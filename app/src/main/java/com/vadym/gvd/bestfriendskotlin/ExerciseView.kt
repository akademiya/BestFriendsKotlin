package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class ExerciseView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_exercise)
        toolbarButtonMenu()

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_exercises)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        val adContainer: AdView = findViewById(R.id.adViewExercise)
        val adDivider: View = findViewById(R.id.adDivider)

        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }

        swipeRefresh.setOnRefreshListener {
            Thread(Runnable {
                runOnUiThread { swipeRefresh.isRefreshing }
                try {
                    Thread.sleep(1200)
                    restartActivity(this)
//                    startActivity(Intent(this, ExerciseView::class.java))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }).start()
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