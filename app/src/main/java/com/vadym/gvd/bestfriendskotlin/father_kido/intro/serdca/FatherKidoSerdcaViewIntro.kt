package com.vadym.gvd.bestfriendskotlin.father_kido.intro.serdca

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinActivity
import com.vadym.gvd.bestfriendskotlin.kidoListPopupMenu

class FatherKidoSerdcaViewIntro : CoinActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    override val coinViewMap = mapOf(
        "coin_father_serdca_36" to R.id.coin_father,
        "coin_father_serdca_37" to R.id.coin_father2,
        "coin_father_serdca_38" to R.id.coin_father3,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)

        setupToolbar()
        setupRecyclerView()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scroll_to_item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_down -> {
                val view: View = findViewById(R.id.action_down)
                kidoListPopupMenu(
                    context = this,
                    view = view,
                    lm = layoutManager,
                    kidoSize = 32
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

        val kido = (1..32).map { i ->
            KidoSerdca(
                getString(resources.getIdentifier("pr_serdca_$i", "string", packageName)),
                getString(resources.getIdentifier("pr_serdca_${i}t", "string", packageName))
            )
        }

        rv.adapter = KidoSerdcaAdapter(kido)
    }

}