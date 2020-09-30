package com.vadym.gvd.bestfriendskotlin.father_kido.intro.yongye

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.LinearLayout
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class KidoYongyeView : MainActivity() {

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

        tracker().setScreenName("Kido Yongye")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        init()
    }

    private fun init() {
        val rv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoYongye>()
        kido.add(KidoYongye(getString(R.string.b_yongye_1), getString(R.string.pr_yongye_1t)))
        kido.add(KidoYongye(getString(R.string.b_yongye_2), getString(R.string.pr_yongye_2t)))
        kido.add(KidoYongye(getString(R.string.b_yongye_3), getString(R.string.pr_yongye_3t)))
        kido.add(KidoYongye(getString(R.string.b_yongye_4), getString(R.string.pr_yongye_4t)))
        kido.add(KidoYongye(getString(R.string.b_yongye_5), getString(R.string.pr_yongye_5t)))
        kido.add(KidoYongye(getString(R.string.b_yongye_6), getString(R.string.pr_yongye_6t)))
        kido.add(KidoYongye(getString(R.string.b_yongye_7), getString(R.string.pr_yongye_7t)))

        val adapter = KidoYongyeAdapter(kido)
        rv.adapter = adapter
    }
}