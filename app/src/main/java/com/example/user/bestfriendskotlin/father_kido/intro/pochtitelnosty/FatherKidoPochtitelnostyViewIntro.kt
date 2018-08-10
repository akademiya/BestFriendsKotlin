package com.example.user.bestfriendskotlin.father_kido.intro.pochtitelnosty

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.tracker

class FatherKidoPochtitelnostyViewIntro : MainActivity() {

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

        tracker().setScreenName("Kido Pochtitelnosty")
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoPochtitelnosty>()
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_1), getString(R.string.pr_pochtitelnosty_1t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_2), getString(R.string.pr_pochtitelnosty_2t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_3), getString(R.string.pr_pochtitelnosty_3t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_4), getString(R.string.pr_pochtitelnosty_4t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_5), getString(R.string.pr_pochtitelnosty_5t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_6), getString(R.string.pr_pochtitelnosty_6t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_7), getString(R.string.pr_pochtitelnosty_7t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_8), getString(R.string.pr_pochtitelnosty_8t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_9), getString(R.string.pr_pochtitelnosty_9t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_10), getString(R.string.pr_pochtitelnosty_10t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_11), getString(R.string.pr_pochtitelnosty_11t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_12), getString(R.string.pr_pochtitelnosty_12t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_13), getString(R.string.pr_pochtitelnosty_13t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_14), getString(R.string.pr_pochtitelnosty_14t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_15), getString(R.string.pr_pochtitelnosty_15t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_16), getString(R.string.pr_pochtitelnosty_16t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_17), getString(R.string.pr_pochtitelnosty_17t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_18), getString(R.string.pr_pochtitelnosty_18t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_19), getString(R.string.pr_pochtitelnosty_19t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_20), getString(R.string.pr_pochtitelnosty_20t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_21), getString(R.string.pr_pochtitelnosty_21t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_22), getString(R.string.pr_pochtitelnosty_22t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_23), getString(R.string.pr_pochtitelnosty_23t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_24), getString(R.string.pr_pochtitelnosty_24t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_25), getString(R.string.pr_pochtitelnosty_25t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_26), getString(R.string.pr_pochtitelnosty_26t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_27), getString(R.string.pr_pochtitelnosty_27t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_28), getString(R.string.pr_pochtitelnosty_28t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_29), getString(R.string.pr_pochtitelnosty_29t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_30), getString(R.string.pr_pochtitelnosty_30t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_31), getString(R.string.pr_pochtitelnosty_31t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_32), getString(R.string.pr_pochtitelnosty_32t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_33), getString(R.string.pr_pochtitelnosty_33t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_34), getString(R.string.pr_pochtitelnosty_34t)))
        kido.add(KidoPochtitelnosty(getString(R.string.pr_pochtitelnosty_35), getString(R.string.pr_pochtitelnosty_35t)))

        val adapter = KidoPochtitelnostyAdapter(kido)
        rv.adapter = adapter
    }
}