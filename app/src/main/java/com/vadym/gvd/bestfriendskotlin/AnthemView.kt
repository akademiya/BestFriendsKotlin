package com.vadym.gvd.bestfriendskotlin

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.view_anthem.*
import java.io.IOException


class AnthemView : MainActivity() {

    private lateinit var cheonIlGukAnthem: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_anthem)
        tracker().setScreenName("Anthem")
        tracker().send(HitBuilders.ScreenViewBuilder().build())

        cheonIlGukAnthem = MediaPlayer.create(this, R.raw.cheon_il_guk_anthem)

        toolbarButtonMenu(cheonIlGukAnthem)
        iv_stop.isEnabled = false
        iv_pause.isEnabled = false

        iv_play.setOnClickListener {
            if (!cheonIlGukAnthem.isPlaying) {
                try {
                    cheonIlGukAnthem.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                iv_play.isEnabled = false
                iv_pause.isEnabled = true
                iv_stop.isEnabled = true
                Toast.makeText(this, resources.getString(R.string.loading_mp3), Toast.LENGTH_SHORT).show()
            }
        }
        iv_pause.setOnClickListener {
            if (cheonIlGukAnthem.isPlaying) {
                cheonIlGukAnthem.pause()
                iv_play.isEnabled = true
                iv_pause.isEnabled = false
                iv_stop.isEnabled = false
            }
        }
        iv_stop.setOnClickListener {
            if (cheonIlGukAnthem.isPlaying){
                cheonIlGukAnthem.stop()
                cheonIlGukAnthem.prepareAsync()
                iv_play.isEnabled = true
                iv_pause.isEnabled = false
                iv_stop.isEnabled = false
            }
        }
    }

    private fun toolbarButtonMenu(cheonIlGukAnthem: MediaPlayer) {
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