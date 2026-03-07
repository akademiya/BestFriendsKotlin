package com.vadym.gvd.bestfriendskotlin.father_kido.intro.loyalty

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kidoListPopupMenu

class FatherKidoLoyaltyViewIntro : MainActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

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
                    kidoSize = 30
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

        val kido = (1..30).map { i ->
            KidoLoyalty(
                getString(resources.getIdentifier("pr_loyalty_$i", "string", packageName)),
                getString(resources.getIdentifier("pr_loyalty_${i}t", "string", packageName))
            )
        }

        rv.adapter = KidoLoyaltyAdapter(kido)
    }
}