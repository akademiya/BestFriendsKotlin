package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseView : MainActivity() {

    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_exercise)
        setupToolbar()

        youTubePlayerView = findViewById(R.id.youtube_exercises)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        val adContainer: AdView = findViewById(R.id.adViewExercise)
        val adDivider: View = findViewById(R.id.adDivider)


        if (isNetworkAvailable()) {
//            initYouTubePlayer()
            lifecycle.addObserver(youTubePlayerView)
            window.decorView.post {
                adContainer.visibility = View.VISIBLE
                adDivider.visibility = View.VISIBLE
                Admob.initializeAdmob(this, adContainer)
            }
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
            youTubePlayerView.visibility = View.GONE
        }

        swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                delay(1200)
                swipeRefresh.isRefreshing = false
                restartActivity(this@ExerciseView)
            }
        }
    }

    private fun initYouTubePlayer() {
//        val videoId = "6_QzQ5KRDw8"
//            getString(R.string.youtube_video_id)
//        youTubePlayerView.enableAutomaticInitialization = false
//        lifecycle.addObserver(youTubePlayerView)
//
//
//        val iFramePlayerOptions = IFramePlayerOptions.Builder()
//            .controls(1)
//            .rel(0)
//            .build()
//
//        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                super.onReady(youTubePlayer)
//                youTubePlayer.cueVideo(videoId, 0f)
//            }
//            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
//                val intent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://www.youtube.com/watch?v=$videoId")
//                )
//                startActivity(intent)
//            }
//        })

//        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                youTubePlayer.loadVideo(videoId, 0f)
//            }
//        })
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }
}