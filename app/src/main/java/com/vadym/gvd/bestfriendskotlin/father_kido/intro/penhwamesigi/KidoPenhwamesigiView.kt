package com.vadym.gvd.bestfriendskotlin.father_kido.intro.penhwamesigi

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoPenhwamesigiView : MainActivity() {

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

        tracker().setScreenName("Kido Penhwamesigi")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoPenhwamesigi>()
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_1), getString(R.string.pr_penhwamesigi_1t)))
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_2), getString(R.string.pr_penhwamesigi_2t)))
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_3), getString(R.string.pr_penhwamesigi_3t)))
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_4), getString(R.string.pr_penhwamesigi_4t)))
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_5), getString(R.string.pr_penhwamesigi_5t)))
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_6), getString(R.string.pr_penhwamesigi_6t)))
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_7), getString(R.string.pr_penhwamesigi_7t)))
        kido.add(KidoPenhwamesigi(getString(R.string.b_penhwamesigi_8), getString(R.string.pr_penhwamesigi_8t)))

        val adapter = KidoPenhwamesigiAdapter(kido)
        rv.adapter = adapter
    }
}