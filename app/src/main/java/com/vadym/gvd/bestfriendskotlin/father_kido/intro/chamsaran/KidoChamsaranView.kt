package com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamsaran

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R

class KidoChamsaranView : MainActivity() {

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

        val kido = ArrayList<KidoChamsaran>()
        kido.add(KidoChamsaran(getString(R.string.b_chamsaran_1), getString(R.string.pr_chamsaran_1t)))
        kido.add(KidoChamsaran(getString(R.string.b_chamsaran_2), getString(R.string.pr_chamsaran_2t)))
        kido.add(KidoChamsaran(getString(R.string.b_chamsaran_3), getString(R.string.pr_chamsaran_3t)))
        kido.add(KidoChamsaran(getString(R.string.b_chamsaran_4), getString(R.string.pr_chamsaran_4t)))
        kido.add(KidoChamsaran(getString(R.string.b_chamsaran_5), getString(R.string.pr_chamsaran_5t)))
        kido.add(KidoChamsaran(getString(R.string.b_chamsaran_6), getString(R.string.pr_chamsaran_6t)))
        kido.add(KidoChamsaran(getString(R.string.b_chamsaran_7), getString(R.string.pr_chamsaran_7t)))

        val adapter = KidoChamsaranAdapter(kido)
        rv.adapter = adapter
    }
}