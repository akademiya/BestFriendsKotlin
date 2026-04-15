package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseView : CoinActivity() {

    private lateinit var youTubePlayerView: YouTubePlayerView

    override val coinViewMap = mapOf(
        "coin_exercise_4" to R.id.coin_exercise_4,
        "coin_exercise_5" to R.id.coin_exercise_5,
        "coin_exercise_6" to R.id.coin_exercise_6,
        "coin_exercise_7" to R.id.coin_exercise_7
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_exercise)
        setupToolbar()
        setupBackPress()

        youTubePlayerView = findViewById(R.id.youtube_exercises)
        val watchInYoutubeButton = findViewById<ImageButton>(R.id.watch_in_youtube)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        val adContainer: AdView = findViewById(R.id.adViewExercise)
        val adDivider: View = findViewById(R.id.adDivider)
        initYouTubePlayer()

        watchInYoutubeButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=6_QzQ5KRDw8")
            )
            startActivity(intent)
        }

        AdManager.setupBanner(this, adContainer, adDivider)

        swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                delay(1200)
                swipeRefresh.isRefreshing = false
                restartActivity(this@ExerciseView)
            }
        }
    }

    private fun initYouTubePlayer() {
        val videoId = "6_QzQ5KRDw8"

//        youTubePlayerView.enableAutomaticInitialization = false
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
            navigateBack()
        }
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        AdManager.init(this)
        AdManager.tryShowOnAppStart(this) {
            startActivity(
                Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    putExtra(EXTRA_OPEN_DRAWER, true)
                }
            )
            finish()
        }
    }
}