package com.vadym.gvd.bestfriendskotlin.father_kido.intro.mesia

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoMesiaView : MainActivity() {

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

        tracker().setScreenName("Kido Mesia")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoMesia>()
        kido.add(KidoMesia(getString(R.string.b_mesia_1), getString(R.string.pr_mesia_1t)))
        kido.add(KidoMesia(getString(R.string.b_mesia_2), getString(R.string.pr_mesia_2t)))
        kido.add(KidoMesia(getString(R.string.b_mesia_3), getString(R.string.pr_mesia_3t)))
        kido.add(KidoMesia(getString(R.string.b_mesia_4), getString(R.string.pr_mesia_4t)))
        kido.add(KidoMesia(getString(R.string.b_mesia_5), getString(R.string.pr_mesia_5t)))
        kido.add(KidoMesia(getString(R.string.b_mesia_6), getString(R.string.pr_mesia_6t)))
        kido.add(KidoMesia(getString(R.string.b_mesia_7), getString(R.string.pr_mesia_7t)))

        val adapter = KidoMesiaAdapter(kido)
        rv.adapter = adapter
    }
}