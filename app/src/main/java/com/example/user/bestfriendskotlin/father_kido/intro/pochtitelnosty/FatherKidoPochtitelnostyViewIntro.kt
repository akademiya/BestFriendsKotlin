package com.example.user.bestfriendskotlin.father_kido.intro.pochtitelnosty

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.AndroidApplication
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.father_kido.intro.ReadTextFromFile
import com.example.user.bestfriendskotlin.tracker
import java.io.InputStream

class FatherKidoPochtitelnostyViewIntro : MainActivity(), ReadTextFromFile {

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

        val adapter = KidoPochtitelnostyAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}