package com.vadym.gvd.bestfriendskotlin.father_kido.intro.champumo

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoChampumoView : MainActivity() {

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

        tracker().setScreenName("Kido Champumo")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoChampumo>()
        kido.add(KidoChampumo(getString(R.string.b_champumo_1), getString(R.string.pr_champumo_1t)))
        kido.add(KidoChampumo(getString(R.string.b_champumo_2), getString(R.string.pr_champumo_2t)))
        kido.add(KidoChampumo(getString(R.string.b_champumo_3), getString(R.string.pr_champumo_3t)))
        kido.add(KidoChampumo(getString(R.string.b_champumo_4), getString(R.string.pr_champumo_4t)))
        kido.add(KidoChampumo(getString(R.string.b_champumo_5), getString(R.string.pr_champumo_5t)))
        kido.add(KidoChampumo(getString(R.string.b_champumo_6), getString(R.string.pr_champumo_6t)))
        kido.add(KidoChampumo(getString(R.string.b_champumo_7), getString(R.string.pr_champumo_7t)))

        val adapter = KidoChampumoAdapter(kido)
        rv.adapter = adapter
    }
}