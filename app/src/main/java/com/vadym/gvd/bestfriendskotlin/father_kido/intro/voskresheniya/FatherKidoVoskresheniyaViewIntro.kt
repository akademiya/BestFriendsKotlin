package com.vadym.gvd.bestfriendskotlin.father_kido.intro.voskresheniya

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

class FatherKidoVoskresheniyaViewIntro : MainActivity()  {

    private lateinit var rv: RecyclerView
    private val kido = ArrayList<KidoVoskresheniya>()

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

        tracker().setScreenName("Kido Voskresheniya")
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
                kidoListPopupMenu(context = this, view = view, rv = rv, kidoSize = 35, positionHide = 35)
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

        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_1), getString(R.string.pr_voskresheniya_1t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_2), getString(R.string.pr_voskresheniya_2t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_3), getString(R.string.pr_voskresheniya_3t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_4), getString(R.string.pr_voskresheniya_4t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_5), getString(R.string.pr_voskresheniya_5t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_6), getString(R.string.pr_voskresheniya_6t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_7), getString(R.string.pr_voskresheniya_7t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_8), getString(R.string.pr_voskresheniya_8t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_9), getString(R.string.pr_voskresheniya_9t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_10), getString(R.string.pr_voskresheniya_10t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_11), getString(R.string.pr_voskresheniya_11t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_12), getString(R.string.pr_voskresheniya_12t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_13), getString(R.string.pr_voskresheniya_13t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_14), getString(R.string.pr_voskresheniya_14t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_15), getString(R.string.pr_voskresheniya_15t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_16), getString(R.string.pr_voskresheniya_16t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_17), getString(R.string.pr_voskresheniya_17t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_18), getString(R.string.pr_voskresheniya_18t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_19), getString(R.string.pr_voskresheniya_19t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_20), getString(R.string.pr_voskresheniya_20t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_21), getString(R.string.pr_voskresheniya_21t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_22), getString(R.string.pr_voskresheniya_22t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_23), getString(R.string.pr_voskresheniya_23t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_24), getString(R.string.pr_voskresheniya_24t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_25), getString(R.string.pr_voskresheniya_25t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_26), getString(R.string.pr_voskresheniya_26t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_27), getString(R.string.pr_voskresheniya_27t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_28), getString(R.string.pr_voskresheniya_28t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_29), getString(R.string.pr_voskresheniya_29t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_30), getString(R.string.pr_voskresheniya_30t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_31), getString(R.string.pr_voskresheniya_31t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_32), getString(R.string.pr_voskresheniya_32t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_33), getString(R.string.pr_voskresheniya_33t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_34), getString(R.string.pr_voskresheniya_34t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_35), getString(R.string.pr_voskresheniya_35t)))

        val adapter = KidoVoskresheniyaAdapter(kido)
        rv.adapter = adapter
    }
}