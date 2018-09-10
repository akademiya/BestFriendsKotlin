package com.example.user.bestfriendskotlin.father_kido.intro.devotion

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.tracker
import kotlinx.android.synthetic.main.view_father_kido_intro.*

class FatherKidoDevotionViewIntro: MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_intro)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        tracker().setScreenName("Kido Devotion")
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoDevotion>()
        kido.add(KidoDevotion(getString(R.string.pr_devotion_1), getString(R.string.pr_devotion_1t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_2), getString(R.string.pr_devotion_2t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_3), getString(R.string.pr_devotion_3t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_4), getString(R.string.pr_devotion_4t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_5), getString(R.string.pr_devotion_5t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_6), getString(R.string.pr_devotion_6t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_7), getString(R.string.pr_devotion_7t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_8), getString(R.string.pr_devotion_8t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_9), getString(R.string.pr_devotion_9t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_10), getString(R.string.pr_devotion_10t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_11), getString(R.string.pr_devotion_11t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_12), getString(R.string.pr_devotion_12t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_13), getString(R.string.pr_devotion_13t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_14), getString(R.string.pr_devotion_14t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_15), getString(R.string.pr_devotion_15t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_16), getString(R.string.pr_devotion_16t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_17), getString(R.string.pr_devotion_17t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_18), getString(R.string.pr_devotion_18t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_19), getString(R.string.pr_devotion_19t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_20), getString(R.string.pr_devotion_20t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_21), getString(R.string.pr_devotion_21t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_22), getString(R.string.pr_devotion_22t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_23), getString(R.string.pr_devotion_23t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_24), getString(R.string.pr_devotion_24t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_25), getString(R.string.pr_devotion_25t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_26), getString(R.string.pr_devotion_26t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_27), getString(R.string.pr_devotion_27t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_28), getString(R.string.pr_devotion_28t)))
        kido.add(KidoDevotion(getString(R.string.pr_devotion_29), getString(R.string.pr_devotion_29t)))

        val adapter = KidoDevotionAdapter(kido)
        rv.adapter = adapter
    }
}