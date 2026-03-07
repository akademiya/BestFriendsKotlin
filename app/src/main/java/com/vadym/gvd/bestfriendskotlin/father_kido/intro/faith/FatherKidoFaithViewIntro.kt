package com.vadym.gvd.bestfriendskotlin.father_kido.intro.faith

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kidoListPopupMenu

class FatherKidoFaithViewIntro : MainActivity() {

    private lateinit var rv: androidx.recyclerview.widget.RecyclerView
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
                    kidoSize = 34
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

        val kido = (1..34).map { i ->
            KidoFaith(
                getString(resources.getIdentifier("pr_faith_$i", "string", packageName)),
                getString(resources.getIdentifier("pr_faith_${i}t", "string", packageName))
            )
        }

        rv.adapter = KidoFaithAdapter(kido)
    }

}