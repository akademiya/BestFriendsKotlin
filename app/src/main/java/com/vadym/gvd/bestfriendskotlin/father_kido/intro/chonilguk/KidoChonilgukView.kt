package com.vadym.gvd.bestfriendskotlin.father_kido.intro.chonilguk

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoChonilgukView : MainActivity() {

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

        tracker().setScreenName("Kido Chonilguk")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoChonilguk>()
        kido.add(KidoChonilguk(getString(R.string.b_chonilguk_1), getString(R.string.pr_chonilguk_1t)))
        kido.add(KidoChonilguk(getString(R.string.b_chonilguk_2), getString(R.string.pr_chonilguk_2t)))
        kido.add(KidoChonilguk(getString(R.string.b_chonilguk_3), getString(R.string.pr_chonilguk_3t)))
        kido.add(KidoChonilguk(getString(R.string.b_chonilguk_4), getString(R.string.pr_chonilguk_4t)))
        kido.add(KidoChonilguk(getString(R.string.b_chonilguk_5), getString(R.string.pr_chonilguk_5t)))
        kido.add(KidoChonilguk(getString(R.string.b_chonilguk_6), getString(R.string.pr_chonilguk_6t)))
        kido.add(KidoChonilguk(getString(R.string.b_chonilguk_7), getString(R.string.pr_chonilguk_7t)))

        val adapter = KidoChonilgukAdapter(kido)
        rv.adapter = adapter
    }
}