package com.vadym.gvd.bestfriendskotlin.father_kido.intro.penhwasasan

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.LinearLayout
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R

class KidoPenhwasasanView : MainActivity() {

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

        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoPenhwasasan>()
        kido.add(KidoPenhwasasan(getString(R.string.b_penhwasasan_1), getString(R.string.pr_penhwasasan_1t)))
        kido.add(KidoPenhwasasan(getString(R.string.b_penhwasasan_2), getString(R.string.pr_penhwasasan_2t)))
        kido.add(KidoPenhwasasan(getString(R.string.b_penhwasasan_3), getString(R.string.pr_penhwasasan_3t)))
        kido.add(KidoPenhwasasan(getString(R.string.b_penhwasasan_4), getString(R.string.pr_penhwasasan_4t)))
        kido.add(KidoPenhwasasan(getString(R.string.b_penhwasasan_5), getString(R.string.pr_penhwasasan_5t)))
        kido.add(KidoPenhwasasan(getString(R.string.b_penhwasasan_6), getString(R.string.pr_penhwasasan_6t)))
        kido.add(KidoPenhwasasan(getString(R.string.b_penhwasasan_7), getString(R.string.pr_penhwasasan_7t)))

        val adapter = KidoPenhwasasanAdapter(kido)
        rv.adapter = adapter
    }
}