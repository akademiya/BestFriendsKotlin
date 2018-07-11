package com.example.user.bestfriendskotlin.father_kido.intro.serdca

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

class FatherKidoSerdcaViewIntro : MainActivity(), ReadTextFromFile {

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

        tracker().setScreenName("Kido Serdca")
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoSerdca>()
        kido.add(KidoSerdca(getString(R.string.pr_serdca_1), getString(R.string.pr_serdca_1t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_2), getString(R.string.pr_serdca_2t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_3), getString(R.string.pr_serdca_3t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_4), getString(R.string.pr_serdca_4t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_5), getString(R.string.pr_serdca_5t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_6), getString(R.string.pr_serdca_6t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_7), getString(R.string.pr_serdca_7t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_8), getString(R.string.pr_serdca_8t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_9), getString(R.string.pr_serdca_9t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_10), getString(R.string.pr_serdca_10t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_11), getString(R.string.pr_serdca_11t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_12), getString(R.string.pr_serdca_12t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_13), getString(R.string.pr_serdca_13t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_14), getString(R.string.pr_serdca_14t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_15), getString(R.string.pr_serdca_15t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_16), getString(R.string.pr_serdca_16t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_17), getString(R.string.pr_serdca_17t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_18), getString(R.string.pr_serdca_18t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_19), getString(R.string.pr_serdca_19t)))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_20), getString(R.string.pr_serdca_20t)))

        val adapter = KidoSerdcaAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}