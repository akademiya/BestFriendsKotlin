package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinActivity
import java.io.IOException


class AnthemView : CoinActivity() {

    private lateinit var cheonIlGukAnthem: MediaPlayer

    override val coinViewMap = mapOf(
        "coin_anthem_14" to R.id.coin_anthem_14,
        "coin_anthem_15" to R.id.coin_anthem_15,
        "coin_anthem_16" to R.id.coin_anthem_16
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_anthem)
        setupBackPress()

        val stop = findViewById<ImageView>(R.id.iv_stop)
        val pause = findViewById<ImageView>(R.id.iv_pause)
        val play = findViewById<ImageView>(R.id.iv_play)

        cheonIlGukAnthem = MediaPlayer.create(this, R.raw.cheon_il_guk_anthem)

        toolbarButtonMenu(cheonIlGukAnthem)
        stop.isEnabled = false
        pause.isEnabled = false

        play.setOnClickListener {
            if (!cheonIlGukAnthem.isPlaying) {
                try {
                    cheonIlGukAnthem.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                play.isEnabled = false
                pause.isEnabled = true
                stop.isEnabled = true
                Toast.makeText(this, resources.getString(R.string.loading_mp3), Toast.LENGTH_SHORT).show()
            }
        }
        pause.setOnClickListener {
            if (cheonIlGukAnthem.isPlaying) {
                cheonIlGukAnthem.pause()
                play.isEnabled = true
                pause.isEnabled = false
                stop.isEnabled = false
            }
        }
        stop.setOnClickListener {
            if (cheonIlGukAnthem.isPlaying){
                cheonIlGukAnthem.stop()
                cheonIlGukAnthem.prepareAsync()
                play.isEnabled = true
                pause.isEnabled = false
                stop.isEnabled = false
            }
        }
    }

    private fun toolbarButtonMenu(cheonIlGukAnthem: MediaPlayer) {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            cheonIlGukAnthem.stop()
            navigateBack()
        }
    }

    override fun onPause() {
        cheonIlGukAnthem.stop()
        super.onPause()
    }

    override fun onDestroy() {
        cheonIlGukAnthem.stop()
        super.onDestroy()
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