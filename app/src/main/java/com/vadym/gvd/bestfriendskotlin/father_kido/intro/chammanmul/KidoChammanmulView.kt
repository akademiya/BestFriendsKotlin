package com.vadym.gvd.bestfriendskotlin.father_kido.intro.chammanmul

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R

class KidoChammanmulView : MainActivity() {

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

        val kido = ArrayList<KidoChammanmul>()
        kido.add(KidoChammanmul(getString(R.string.b_chammanmul_1), getString(R.string.pr_chammanmul_1t)))
        kido.add(KidoChammanmul(getString(R.string.b_chammanmul_2), getString(R.string.pr_chammanmul_2t)))
        kido.add(KidoChammanmul(getString(R.string.b_chammanmul_3), getString(R.string.pr_chammanmul_3t)))
        kido.add(KidoChammanmul(getString(R.string.b_chammanmul_4), getString(R.string.pr_chammanmul_4t)))
        kido.add(KidoChammanmul(getString(R.string.b_chammanmul_5), getString(R.string.pr_chammanmul_5t)))
        kido.add(KidoChammanmul(getString(R.string.b_chammanmul_6), getString(R.string.pr_chammanmul_6t)))
        kido.add(KidoChammanmul(getString(R.string.b_chammanmul_7), getString(R.string.pr_chammanmul_7t)))

        val adapter = KidoChammanmulAdapter(kido)
        rv.adapter = adapter
    }
}