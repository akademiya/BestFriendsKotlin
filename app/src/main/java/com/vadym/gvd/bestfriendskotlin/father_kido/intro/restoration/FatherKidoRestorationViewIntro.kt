package com.vadym.gvd.bestfriendskotlin.father_kido.intro.restoration

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kidoListPopupMenu
import com.vadym.gvd.bestfriendskotlin.tracker
import kotlinx.android.synthetic.main.view_father_kido_intro.*

class FatherKidoRestorationViewIntro : MainActivity() {

    private lateinit var rv: RecyclerView
    private val kido = ArrayList<KidoRestoration>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        tracker().setScreenName("Kido Restoration")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scroll_to_item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view: View = findViewById(R.id.action_down)

        when (item.itemId) {
            R.id.action_down -> {
                kidoListPopupMenu(context = this, view = view, rv = rv, kidoSize = 36, positionHide = 36)
                return true
            }
            R.id.action_up -> {
                rv.scrollToPosition(0)
                return true
            }
            else -> null
        }

        return onOptionsItemSelected(item)
    }

    private fun init() {
        rv = findViewById(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        kido.add(KidoRestoration(getString(R.string.pr_restoration_1), getString(R.string.pr_restoration_1t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_2), getString(R.string.pr_restoration_2t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_3), getString(R.string.pr_restoration_3t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_4), getString(R.string.pr_restoration_4t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_5), getString(R.string.pr_restoration_5t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_6), getString(R.string.pr_restoration_6t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_7), getString(R.string.pr_restoration_7t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_8), getString(R.string.pr_restoration_8t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_9), getString(R.string.pr_restoration_9t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_10), getString(R.string.pr_restoration_10t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_11), getString(R.string.pr_restoration_11t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_12), getString(R.string.pr_restoration_12t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_13), getString(R.string.pr_restoration_13t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_14), getString(R.string.pr_restoration_14t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_15), getString(R.string.pr_restoration_15t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_16), getString(R.string.pr_restoration_16t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_17), getString(R.string.pr_restoration_17t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_18), getString(R.string.pr_restoration_18t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_19), getString(R.string.pr_restoration_19t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_20), getString(R.string.pr_restoration_20t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_21), getString(R.string.pr_restoration_21t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_22), getString(R.string.pr_restoration_22t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_23), getString(R.string.pr_restoration_23t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_24), getString(R.string.pr_restoration_24t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_25), getString(R.string.pr_restoration_25t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_26), getString(R.string.pr_restoration_26t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_27), getString(R.string.pr_restoration_27t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_28), getString(R.string.pr_restoration_28t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_29), getString(R.string.pr_restoration_29t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_30), getString(R.string.pr_restoration_30t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_31), getString(R.string.pr_restoration_31t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_32), getString(R.string.pr_restoration_32t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_33), getString(R.string.pr_restoration_33t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_34), getString(R.string.pr_restoration_34t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_35), getString(R.string.pr_restoration_35t)))
        kido.add(KidoRestoration(getString(R.string.pr_restoration_36), getString(R.string.pr_restoration_36t)))

        val adapter = KidoRestorationAdapter(kido)
        rv.adapter = adapter
    }
}