package com.vadym.gvd.bestfriendskotlin.father_kido.intro.nadezdy

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


class FatherKidoNadezdyViewIntro : MainActivity() {

    private lateinit var rv: RecyclerView
    private val kido = ArrayList<KidoNadezdy>()

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

        tracker().setScreenName("Kido Nadezdy")
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
                kidoListPopupMenu(context = this, view = view, rv = rv, kidoSize = 39, positionHide = 39)
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

        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_1), getString(R.string.pr_nadezdy_1t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_2), getString(R.string.pr_nadezdy_2t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_3), getString(R.string.pr_nadezdy_3t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_4), getString(R.string.pr_nadezdy_4t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_5), getString(R.string.pr_nadezdy_5t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_6), getString(R.string.pr_nadezdy_6t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_7), getString(R.string.pr_nadezdy_7t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_8), getString(R.string.pr_nadezdy_8t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_9), getString(R.string.pr_nadezdy_9t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_10), getString(R.string.pr_nadezdy_10t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_11), getString(R.string.pr_nadezdy_11t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_12), getString(R.string.pr_nadezdy_12t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_13), getString(R.string.pr_nadezdy_13t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_14), getString(R.string.pr_nadezdy_14t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_15), getString(R.string.pr_nadezdy_15t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_16), getString(R.string.pr_nadezdy_16t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_17), getString(R.string.pr_nadezdy_17t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_18), getString(R.string.pr_nadezdy_18t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_19), getString(R.string.pr_nadezdy_19t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_20), getString(R.string.pr_nadezdy_20t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_21), getString(R.string.pr_nadezdy_21t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_22), getString(R.string.pr_nadezdy_22t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_23), getString(R.string.pr_nadezdy_23t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_24), getString(R.string.pr_nadezdy_24t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_25), getString(R.string.pr_nadezdy_25t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_26), getString(R.string.pr_nadezdy_26t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_27), getString(R.string.pr_nadezdy_27t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_28), getString(R.string.pr_nadezdy_28t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_29), getString(R.string.pr_nadezdy_29t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_30), getString(R.string.pr_nadezdy_30t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_31), getString(R.string.pr_nadezdy_31t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_32), getString(R.string.pr_nadezdy_32t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_33), getString(R.string.pr_nadezdy_33t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_34), getString(R.string.pr_nadezdy_34t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_35), getString(R.string.pr_nadezdy_35t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_36), getString(R.string.pr_nadezdy_36t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_37), getString(R.string.pr_nadezdy_37t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_38), getString(R.string.pr_nadezdy_38t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_39), getString(R.string.pr_nadezdy_39t)))

        val adapter = KidoNadezdyAdapter(kido)
        rv.adapter = adapter
    }
}