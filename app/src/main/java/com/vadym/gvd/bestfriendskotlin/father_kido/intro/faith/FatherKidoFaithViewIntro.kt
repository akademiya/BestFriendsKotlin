package com.vadym.gvd.bestfriendskotlin.father_kido.intro.faith

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kidoListPopupMenu

class FatherKidoFaithViewIntro : MainActivity() {

    private lateinit var rv: androidx.recyclerview.widget.RecyclerView
    private val kido = ArrayList<KidoFaith>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scroll_to_item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view: View = findViewById(R.id.action_down)

        return when (item.itemId) {
            R.id.action_down -> {
                kidoListPopupMenu(context = this, view = view, rv = rv, kidoSize = 34, positionHide = 34)
                true
            }
            else -> {
                rv.scrollToPosition(0)
                true
            }
        }

    }

    private fun init() {
        rv = findViewById(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        kido.add(KidoFaith(getString(R.string.pr_faith_1), getString(R.string.pr_faith_1t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_2), getString(R.string.pr_faith_2t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_3), getString(R.string.pr_faith_3t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_4), getString(R.string.pr_faith_4t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_5), getString(R.string.pr_faith_5t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_6), getString(R.string.pr_faith_6t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_7), getString(R.string.pr_faith_7t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_8), getString(R.string.pr_faith_8t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_9), getString(R.string.pr_faith_9t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_10), getString(R.string.pr_faith_10t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_11), getString(R.string.pr_faith_11t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_12), getString(R.string.pr_faith_12t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_13), getString(R.string.pr_faith_13t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_14), getString(R.string.pr_faith_14t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_15), getString(R.string.pr_faith_15t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_16), getString(R.string.pr_faith_16t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_17), getString(R.string.pr_faith_17t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_18), getString(R.string.pr_faith_18t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_19), getString(R.string.pr_faith_19t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_20), getString(R.string.pr_faith_20t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_21), getString(R.string.pr_faith_21t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_22), getString(R.string.pr_faith_22t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_23), getString(R.string.pr_faith_23t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_24), getString(R.string.pr_faith_24t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_25), getString(R.string.pr_faith_25t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_26), getString(R.string.pr_faith_26t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_27), getString(R.string.pr_faith_27t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_28), getString(R.string.pr_faith_28t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_29), getString(R.string.pr_faith_29t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_30), getString(R.string.pr_faith_30t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_32), getString(R.string.pr_faith_31t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_32), getString(R.string.pr_faith_32t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_33), getString(R.string.pr_faith_33t)))
        kido.add(KidoFaith(getString(R.string.pr_faith_34), getString(R.string.pr_faith_34t)))

        val adapter = KidoFaithAdapter(kido)
        rv.adapter = adapter
    }
}