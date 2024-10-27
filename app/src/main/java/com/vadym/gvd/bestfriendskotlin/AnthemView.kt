package com.vadym.gvd.bestfriendskotlin

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.io.IOException


class AnthemView : MainActivity() {

    private lateinit var cheonIlGukAnthem: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_anthem)

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
            onBackPressed()
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

}