package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
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

        // Реєструємо player у lifecycle — обов'язково!
//        lifecycle.addObserver(youTubePlayerView)

        if (isNetworkAvailable()) {
            initYouTubePlayer()
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
        val videoId = getString(R.string.youtube_video_id)
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=$videoId")
                )
                startActivity(intent)
            }
        })
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