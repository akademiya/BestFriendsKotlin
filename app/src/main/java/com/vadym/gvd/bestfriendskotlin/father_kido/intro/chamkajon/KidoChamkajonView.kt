package com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamkajon

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoChamkajonView : MainActivity() {

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

        tracker().setScreenName("Kido Chamkajon")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoChamkajon>()
        kido.add(KidoChamkajon(getString(R.string.b_chamkajon_1), getString(R.string.pr_chamkajon_1t)))
        kido.add(KidoChamkajon(getString(R.string.b_chamkajon_2), getString(R.string.pr_chamkajon_2t)))
        kido.add(KidoChamkajon(getString(R.string.b_chamkajon_3), getString(R.string.pr_chamkajon_3t)))
        kido.add(KidoChamkajon(getString(R.string.b_chamkajon_4), getString(R.string.pr_chamkajon_4t)))
        kido.add(KidoChamkajon(getString(R.string.b_chamkajon_5), getString(R.string.pr_chamkajon_5t)))
        kido.add(KidoChamkajon(getString(R.string.b_chamkajon_6), getString(R.string.pr_chamkajon_6t)))
        kido.add(KidoChamkajon(getString(R.string.b_chamkajon_7), getString(R.string.pr_chamkajon_7t)))

        val adapter = KidoChamkajonAdapter(kido)
        rv.adapter = adapter
    }
}