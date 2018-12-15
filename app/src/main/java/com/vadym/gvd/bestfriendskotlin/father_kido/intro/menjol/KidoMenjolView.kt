package com.vadym.gvd.bestfriendskotlin.father_kido.intro.menjol

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoMenjolView : MainActivity() {

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

        tracker().setScreenName("Kido Menjol")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoMenjol>()
        kido.add(KidoMenjol(getString(R.string.b_menjol_1), getString(R.string.pr_menjol_1t)))
        kido.add(KidoMenjol(getString(R.string.b_menjol_2), getString(R.string.pr_menjol_2t)))
        kido.add(KidoMenjol(getString(R.string.b_menjol_3), getString(R.string.pr_menjol_3t)))
        kido.add(KidoMenjol(getString(R.string.b_menjol_4), getString(R.string.pr_menjol_4t)))
        kido.add(KidoMenjol(getString(R.string.b_menjol_5), getString(R.string.pr_menjol_5t)))
        kido.add(KidoMenjol(getString(R.string.b_menjol_6), getString(R.string.pr_menjol_6t)))
        kido.add(KidoMenjol(getString(R.string.b_menjol_7), getString(R.string.pr_menjol_7t)))

        val adapter = KidoMenjolAdapter(kido)
        rv.adapter = adapter
    }
}