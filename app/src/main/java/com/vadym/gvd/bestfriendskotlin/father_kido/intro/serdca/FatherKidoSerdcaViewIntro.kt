package com.vadym.gvd.bestfriendskotlin.father_kido.intro.serdca

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kidoListPopupMenu
import com.vadym.gvd.bestfriendskotlin.tracker

class FatherKidoSerdcaViewIntro : MainActivity() {

    private lateinit var rv: RecyclerView
    private val kido = ArrayList<KidoSerdca>()

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

        tracker().setScreenName("Kido Serdca")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
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
                kidoListPopupMenu(context = this, view = view, rv = rv, kidoSize = 32, positionHide = 32)
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

        kido.add(KidoSerdca(getString(R.string.pr_serdca_1), getString(R.string.pr_serdca_1t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_2), getString(R.string.pr_serdca_2t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_3), getString(R.string.pr_serdca_3t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_4), getString(R.string.pr_serdca_4t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_5), getString(R.string.pr_serdca_5t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_6), getString(R.string.pr_serdca_6t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_7), getString(R.string.pr_serdca_7t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_8), getString(R.string.pr_serdca_8t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_9), getString(R.string.pr_serdca_9t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_10), getString(R.string.pr_serdca_10t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_11), getString(R.string.pr_serdca_11t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_12), getString(R.string.pr_serdca_12t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_13), getString(R.string.pr_serdca_13t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_14), getString(R.string.pr_serdca_14t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_15), getString(R.string.pr_serdca_15t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_16), getString(R.string.pr_serdca_16t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_17), getString(R.string.pr_serdca_17t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_18), getString(R.string.pr_serdca_18t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_19), getString(R.string.pr_serdca_19t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_20), getString(R.string.pr_serdca_20t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_21), getString(R.string.pr_serdca_21t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_22), getString(R.string.pr_serdca_22t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_23), getString(R.string.pr_serdca_23t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_24), getString(R.string.pr_serdca_24t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_25), getString(R.string.pr_serdca_25t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_26), getString(R.string.pr_serdca_26t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_27), getString(R.string.pr_serdca_27t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_28), getString(R.string.pr_serdca_28t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_29), getString(R.string.pr_serdca_29t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_30), getString(R.string.pr_serdca_30t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_31), getString(R.string.pr_serdca_31t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_32), getString(R.string.pr_serdca_32t)))

        val adapter = KidoSerdcaAdapter(kido)
        rv.adapter = adapter
    }
}