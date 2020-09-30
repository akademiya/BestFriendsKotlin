package com.vadym.gvd.bestfriendskotlin.father_kido.intro.devotion

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

class FatherKidoDevotionViewIntro: MainActivity() {

    private lateinit var rv: androidx.recyclerview.widget.RecyclerView
    private val kido = ArrayList<KidoDevotion>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        tracker().setScreenName("Kido Devotion")
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
                kidoListPopupMenu(context = this, view = view, rv = rv, kidoSize = 29, positionHide = 29)
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
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        kido.add(KidoDevotion(getString(R.string.pr_devotion_1), getString(R.string.pr_devotion_1t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_2), getString(R.string.pr_devotion_2t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_3), getString(R.string.pr_devotion_3t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_4), getString(R.string.pr_devotion_4t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_5), getString(R.string.pr_devotion_5t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_6), getString(R.string.pr_devotion_6t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_7), getString(R.string.pr_devotion_7t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_8), getString(R.string.pr_devotion_8t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_9), getString(R.string.pr_devotion_9t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_10), getString(R.string.pr_devotion_10t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_11), getString(R.string.pr_devotion_11t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_12), getString(R.string.pr_devotion_12t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_13), getString(R.string.pr_devotion_13t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_14), getString(R.string.pr_devotion_14t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_15), getString(R.string.pr_devotion_15t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_16), getString(R.string.pr_devotion_16t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_17), getString(R.string.pr_devotion_17t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_18), getString(R.string.pr_devotion_18t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_19), getString(R.string.pr_devotion_19t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_20), getString(R.string.pr_devotion_20t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_21), getString(R.string.pr_devotion_21t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_22), getString(R.string.pr_devotion_22t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_23), getString(R.string.pr_devotion_23t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_24), getString(R.string.pr_devotion_24t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_25), getString(R.string.pr_devotion_25t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_26), getString(R.string.pr_devotion_26t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_27), getString(R.string.pr_devotion_27t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_28), getString(R.string.pr_devotion_28t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_29), getString(R.string.pr_devotion_29t)))

        val adapter = KidoDevotionAdapter(kido)
        rv.adapter = adapter
    }
}