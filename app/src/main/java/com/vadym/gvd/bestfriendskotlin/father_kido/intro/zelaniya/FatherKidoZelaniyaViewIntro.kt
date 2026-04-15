package com.vadym.gvd.bestfriendskotlin.father_kido.intro.zelaniya

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.DarkModePreferences
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinActivity
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.PodcastTtsManager
import com.vadym.gvd.bestfriendskotlin.kidoListPopupMenu

class FatherKidoZelaniyaViewIntro : CoinActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var ttsManager: PodcastTtsManager
    private lateinit var kidoList: List<KidoZelaniya>

    override val coinViewMap = mapOf(
        "coin_father_zelaniya_39" to R.id.coin_father,
        "coin_father_zelaniya_40" to R.id.coin_father2,
        "coin_father_zelaniya_41" to R.id.coin_father3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)

        setupToolbar()
        setupRecyclerView()
        setupPodcast()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scroll_to_item_menu, menu)
        return true
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_down -> {
                val view: View = findViewById(R.id.action_down)
                kidoListPopupMenu(
                    context = this,
                    view = view,
                    lm = layoutManager,
                    kidoSize = 50
                )
                true
            }
            else -> {
                rv.scrollToPosition(0)
                true
            }
        }
    }

    private fun setupRecyclerView() {
        rv = findViewById(R.id.rv_list_father_kido_intro)
        layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.setHasFixedSize(true)

        kidoList = (1..50).map { i ->
            KidoZelaniya(
                getString(resources.getIdentifier("pr_zelaniya_$i", "string", packageName)),
                getString(resources.getIdentifier("pr_zelaniya_${i}t", "string", packageName))
            )
        }

        rv.adapter = KidoZelaniyaAdapter(kidoList)
    }

    private fun setupPodcast() {
        ttsManager = PodcastTtsManager(this)
        val audioPodcastBtn = findViewById<ImageView>(R.id.audio_podcast)
        audioPodcastBtn.visibility = View.VISIBLE
        val kidoTexts = kidoList.map { it.textTitle + it.textDescription }

        ttsManager.bind(
            texts = kidoTexts,
            recyclerView = rv,
            button = findViewById(R.id.audio_podcast)
        )
        audioPodcastBtn.setOnClickListener {
            if (ttsManager.isPlaying) {
                ttsManager.stop()
            } else {
                showStartFromDialog()
            }
        }
    }

    private fun showStartFromDialog() {
        val items = Array(kidoList.size) { i ->
            kidoList[i].textTitle
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.start_from))
            .setItems(items) { _, index ->
                ttsManager.startFrom(index)
            }
            .show()
    }



    override fun onPause() { super.onPause(); ttsManager.onPause() }
    override fun onDestroy() { ttsManager.onDestroy(); super.onDestroy() }

}