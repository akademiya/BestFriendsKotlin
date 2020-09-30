package com.vadym.gvd.bestfriendskotlin.father_kido.intro.suren

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoSurenView : MainActivity() {

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

        tracker().setScreenName("Kido Suren")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoSuren>()
        kido.add(KidoSuren(getString(R.string.b_suren_1), getString(R.string.pr_suren_1t)))
        kido.add(KidoSuren(getString(R.string.b_suren_2), getString(R.string.pr_suren_2t)))
        kido.add(KidoSuren(getString(R.string.b_suren_3), getString(R.string.pr_suren_3t)))
        kido.add(KidoSuren(getString(R.string.b_suren_4), getString(R.string.pr_suren_4t)))
        kido.add(KidoSuren(getString(R.string.b_suren_5), getString(R.string.pr_suren_5t)))
        kido.add(KidoSuren(getString(R.string.b_suren_6), getString(R.string.pr_suren_6t)))
        kido.add(KidoSuren(getString(R.string.b_suren_7), getString(R.string.pr_suren_7t)))

        val adapter = KidoSurenAdapter(kido)
        rv.adapter = adapter
    }
}