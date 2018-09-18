package com.vadym.gvd.bestfriendskotlin.father_kido.intro.zelaniya

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.tracker

class FatherKidoZelaniyaViewIntro : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        tracker().setScreenName("Kido Zelaniya")
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoZelaniya>()
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_1), getString(R.string.pr_zelaniya_1t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_2), getString(R.string.pr_zelaniya_2t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_3), getString(R.string.pr_zelaniya_3t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_4), getString(R.string.pr_zelaniya_4t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_5), getString(R.string.pr_zelaniya_5t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_6), getString(R.string.pr_zelaniya_6t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_7), getString(R.string.pr_zelaniya_7t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_8), getString(R.string.pr_zelaniya_8t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_9), getString(R.string.pr_zelaniya_9t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_10), getString(R.string.pr_zelaniya_10t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_11), getString(R.string.pr_zelaniya_11t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_12), getString(R.string.pr_zelaniya_12t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_13), getString(R.string.pr_zelaniya_13t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_14), getString(R.string.pr_zelaniya_14t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_15), getString(R.string.pr_zelaniya_15t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_16), getString(R.string.pr_zelaniya_16t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_17), getString(R.string.pr_zelaniya_17t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_18), getString(R.string.pr_zelaniya_18t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_19), getString(R.string.pr_zelaniya_19t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_20), getString(R.string.pr_zelaniya_20t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_21), getString(R.string.pr_zelaniya_21t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_22), getString(R.string.pr_zelaniya_22t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_23), getString(R.string.pr_zelaniya_23t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_24), getString(R.string.pr_zelaniya_24t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_25), getString(R.string.pr_zelaniya_25t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_26), getString(R.string.pr_zelaniya_26t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_27), getString(R.string.pr_zelaniya_27t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_28), getString(R.string.pr_zelaniya_28t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_29), getString(R.string.pr_zelaniya_29t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_30), getString(R.string.pr_zelaniya_30t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_31), getString(R.string.pr_zelaniya_31t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_32), getString(R.string.pr_zelaniya_32t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_33), getString(R.string.pr_zelaniya_33t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_34), getString(R.string.pr_zelaniya_34t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_35), getString(R.string.pr_zelaniya_35t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_36), getString(R.string.pr_zelaniya_36t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_37), getString(R.string.pr_zelaniya_37t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_38), getString(R.string.pr_zelaniya_38t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_39), getString(R.string.pr_zelaniya_39t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_40), getString(R.string.pr_zelaniya_40t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_41), getString(R.string.pr_zelaniya_41t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_42), getString(R.string.pr_zelaniya_42t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_43), getString(R.string.pr_zelaniya_43t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_44), getString(R.string.pr_zelaniya_44t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_45), getString(R.string.pr_zelaniya_45t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_46), getString(R.string.pr_zelaniya_46t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_47), getString(R.string.pr_zelaniya_47t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_48), getString(R.string.pr_zelaniya_48t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_49), getString(R.string.pr_zelaniya_49t)))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_50), getString(R.string.pr_zelaniya_50t)))

        val adapter = KidoZelaniyaAdapter(kido)
        rv.adapter = adapter
    }
}