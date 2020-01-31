package com.vadym.gvd.bestfriendskotlin.father_kido.intro.unification

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

class FatherKidoUnificationViewIntro: MainActivity() {

    private lateinit var rv: RecyclerView
    private val kido = ArrayList<KidoUnification>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        tracker().setScreenName("Kido Unification")
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

        kido.add(KidoUnification(getString(R.string.pr_unification_1), getString(R.string.pr_unification_1t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_2), getString(R.string.pr_unification_2t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_3), getString(R.string.pr_unification_3t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_4), getString(R.string.pr_unification_4t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_5), getString(R.string.pr_unification_5t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_6), getString(R.string.pr_unification_6t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_7), getString(R.string.pr_unification_7t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_8), getString(R.string.pr_unification_8t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_9), getString(R.string.pr_unification_9t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_10), getString(R.string.pr_unification_10t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_11), getString(R.string.pr_unification_11t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_12), getString(R.string.pr_unification_12t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_13), getString(R.string.pr_unification_13t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_14), getString(R.string.pr_unification_14t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_15), getString(R.string.pr_unification_15t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_16), getString(R.string.pr_unification_16t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_17), getString(R.string.pr_unification_17t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_18), getString(R.string.pr_unification_18t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_19), getString(R.string.pr_unification_19t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_20), getString(R.string.pr_unification_20t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_21), getString(R.string.pr_unification_21t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_22), getString(R.string.pr_unification_22t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_23), getString(R.string.pr_unification_23t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_24), getString(R.string.pr_unification_24t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_25), getString(R.string.pr_unification_25t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_26), getString(R.string.pr_unification_26t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_27), getString(R.string.pr_unification_27t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_28), getString(R.string.pr_unification_28t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_29), getString(R.string.pr_unification_29t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_30), getString(R.string.pr_unification_30t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_31), getString(R.string.pr_unification_31t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_32), getString(R.string.pr_unification_32t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_33), getString(R.string.pr_unification_33t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_34), getString(R.string.pr_unification_34t)))
        kido.add(KidoUnification(getString(R.string.pr_unification_35), getString(R.string.pr_unification_35t)))

        val adapter = KidoUnificationAdapter(kido)
        rv.adapter = adapter
    }
}