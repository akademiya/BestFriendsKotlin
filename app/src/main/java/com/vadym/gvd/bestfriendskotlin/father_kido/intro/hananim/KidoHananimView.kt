package com.vadym.gvd.bestfriendskotlin.father_kido.intro.hananim

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoHananimView : MainActivity() {

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

        tracker().setScreenName("Kido Hananim")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoHananim>()
        kido.add(KidoHananim(getString(R.string.b_hananim_1), getString(R.string.pr_hananim_1t)))
        kido.add(KidoHananim(getString(R.string.b_hananim_2), getString(R.string.pr_hananim_2t)))
        kido.add(KidoHananim(getString(R.string.b_hananim_3), getString(R.string.pr_hananim_3t)))
        kido.add(KidoHananim(getString(R.string.b_hananim_4), getString(R.string.pr_hananim_4t)))
        kido.add(KidoHananim(getString(R.string.b_hananim_5), getString(R.string.pr_hananim_5t)))
        kido.add(KidoHananim(getString(R.string.b_hananim_6), getString(R.string.pr_hananim_6t)))
        kido.add(KidoHananim(getString(R.string.b_hananim_7), getString(R.string.pr_hananim_7t)))

        val adapter = KidoHananimAdapter(kido)
        rv.adapter = adapter
    }
}