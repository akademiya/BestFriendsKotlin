package com.vadym.gvd.bestfriendskotlin.father_kido.intro.reshimosty

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker
import kotlinx.android.synthetic.main.view_info.*

class FatherKidoReshimostyViewIntro : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        tracker().setScreenName("Kido Reshimosty")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoReshimosty>()
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_1), getString(R.string.pr_reshimosty_1t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_2), getString(R.string.pr_reshimosty_2t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_3), getString(R.string.pr_reshimosty_3t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_4), getString(R.string.pr_reshimosty_4t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_5), getString(R.string.pr_reshimosty_5t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_6), getString(R.string.pr_reshimosty_6t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_7), getString(R.string.pr_reshimosty_7t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_8), getString(R.string.pr_reshimosty_8t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_9), getString(R.string.pr_reshimosty_9t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_10), getString(R.string.pr_reshimosty_10t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_11), getString(R.string.pr_reshimosty_11t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_12), getString(R.string.pr_reshimosty_12t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_13), getString(R.string.pr_reshimosty_13t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_14), getString(R.string.pr_reshimosty_14t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_15), getString(R.string.pr_reshimosty_15t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_16), getString(R.string.pr_reshimosty_16t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_17), getString(R.string.pr_reshimosty_17t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_18), getString(R.string.pr_reshimosty_18t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_19), getString(R.string.pr_reshimosty_19t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_20), getString(R.string.pr_reshimosty_20t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_21), getString(R.string.pr_reshimosty_21t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_22), getString(R.string.pr_reshimosty_22t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_23), getString(R.string.pr_reshimosty_23t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_24), getString(R.string.pr_reshimosty_24t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_25), getString(R.string.pr_reshimosty_25t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_26), getString(R.string.pr_reshimosty_26t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_27), getString(R.string.pr_reshimosty_27t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_28), getString(R.string.pr_reshimosty_28t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_29), getString(R.string.pr_reshimosty_29t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_30), getString(R.string.pr_reshimosty_30t)))
        kido.add(KidoReshimosty(getString(R.string.pr_reshimosty_31), getString(R.string.pr_reshimosty_31t)))

        val adapter = KidoReshimostyAdapter(kido)
        rv.adapter = adapter
    }
}