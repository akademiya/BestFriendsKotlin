package com.vadym.gvd.bestfriendskotlin.father_kido.intro.loyalty

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

class FatherKidoLoyaltyViewIntro : MainActivity() {

    private lateinit var rv: RecyclerView
    private val kido = ArrayList<KidoLoyalty>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        tracker().setScreenName("Kido Loyalty")
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
                kidoListPopupMenu(context = this, view = view, rv = rv, kidoSize = 30, positionHide = 30)
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

        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_1), getString(R.string.pr_loyalty_1t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_2), getString(R.string.pr_loyalty_2t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_3), getString(R.string.pr_loyalty_3t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_4), getString(R.string.pr_loyalty_4t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_5), getString(R.string.pr_loyalty_5t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_6), getString(R.string.pr_loyalty_6t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_7), getString(R.string.pr_loyalty_7t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_8), getString(R.string.pr_loyalty_8t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_9), getString(R.string.pr_loyalty_9t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_10), getString(R.string.pr_loyalty_10t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_11), getString(R.string.pr_loyalty_11t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_12), getString(R.string.pr_loyalty_12t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_13), getString(R.string.pr_loyalty_13t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_14), getString(R.string.pr_loyalty_14t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_15), getString(R.string.pr_loyalty_15t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_16), getString(R.string.pr_loyalty_16t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_17), getString(R.string.pr_loyalty_17t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_18), getString(R.string.pr_loyalty_18t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_19), getString(R.string.pr_loyalty_19t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_20), getString(R.string.pr_loyalty_20t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_21), getString(R.string.pr_loyalty_21t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_22), getString(R.string.pr_loyalty_22t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_23), getString(R.string.pr_loyalty_23t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_24), getString(R.string.pr_loyalty_24t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_25), getString(R.string.pr_loyalty_25t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_26), getString(R.string.pr_loyalty_26t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_27), getString(R.string.pr_loyalty_27t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_28), getString(R.string.pr_loyalty_28t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_29), getString(R.string.pr_loyalty_29t)))
        kido.add(KidoLoyalty(getString(R.string.pr_loyalty_30), getString(R.string.pr_loyalty_30t)))

        val adapter = KidoLoyaltyAdapter(kido)
        rv.adapter = adapter
    }
}