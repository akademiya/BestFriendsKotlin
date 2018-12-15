package com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamingan

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoChaminganView : MainActivity() {

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

        tracker().setScreenName("Kido Chamingan")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoChamingan>()
        kido.add(KidoChamingan(getString(R.string.b_chamingan_1), getString(R.string.pr_chamingan_1t)))
        kido.add(KidoChamingan(getString(R.string.b_chamingan_2), getString(R.string.pr_chamingan_2t)))
        kido.add(KidoChamingan(getString(R.string.b_chamingan_3), getString(R.string.pr_chamingan_3t)))
        kido.add(KidoChamingan(getString(R.string.b_chamingan_4), getString(R.string.pr_chamingan_4t)))
        kido.add(KidoChamingan(getString(R.string.b_chamingan_5), getString(R.string.pr_chamingan_5t)))
        kido.add(KidoChamingan(getString(R.string.b_chamingan_6), getString(R.string.pr_chamingan_6t)))
        kido.add(KidoChamingan(getString(R.string.b_chamingan_7), getString(R.string.pr_chamingan_7t)))

        val adapter = KidoChaminganAdapter(kido)
        rv.adapter = adapter
    }
}