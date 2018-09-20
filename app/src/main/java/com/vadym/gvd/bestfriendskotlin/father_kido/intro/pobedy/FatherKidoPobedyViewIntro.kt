package com.vadym.gvd.bestfriendskotlin.father_kido.intro.pobedy

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class FatherKidoPobedyViewIntro : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        tracker().setScreenName("Kido Pobedy")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoPobedy>()
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_1), getString(R.string.pr_pobedy_1t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_2), getString(R.string.pr_pobedy_2t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_3), getString(R.string.pr_pobedy_3t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_4), getString(R.string.pr_pobedy_4t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_5), getString(R.string.pr_pobedy_5t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_6), getString(R.string.pr_pobedy_6t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_7), getString(R.string.pr_pobedy_7t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_8), getString(R.string.pr_pobedy_8t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_9), getString(R.string.pr_pobedy_9t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_10), getString(R.string.pr_pobedy_10t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_11), getString(R.string.pr_pobedy_11t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_12), getString(R.string.pr_pobedy_12t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_13), getString(R.string.pr_pobedy_13t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_14), getString(R.string.pr_pobedy_14t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_15), getString(R.string.pr_pobedy_15t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_16), getString(R.string.pr_pobedy_16t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_17), getString(R.string.pr_pobedy_17t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_18), getString(R.string.pr_pobedy_18t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_19), getString(R.string.pr_pobedy_19t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_20), getString(R.string.pr_pobedy_20t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_21), getString(R.string.pr_pobedy_21t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_22), getString(R.string.pr_pobedy_22t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_23), getString(R.string.pr_pobedy_23t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_24), getString(R.string.pr_pobedy_24t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_25), getString(R.string.pr_pobedy_25t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_26), getString(R.string.pr_pobedy_26t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_27), getString(R.string.pr_pobedy_27t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_28), getString(R.string.pr_pobedy_28t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_29), getString(R.string.pr_pobedy_29t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_30), getString(R.string.pr_pobedy_30t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_31), getString(R.string.pr_pobedy_31t)))

        val adapter = KidoPobedyAdapter(kido)
        rv.adapter = adapter
    }
}