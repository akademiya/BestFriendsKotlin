package com.example.user.bestfriendskotlin.father_kido.intro.serdca

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.father_kido.intro.ReadTextFromFile
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

        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoSerdca>()
        kido.add(KidoSerdca(getString(R.string.pr_serdca_1), readTextFromFile("pr_serdca_1.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_2), readTextFromFile("pr_serdca_2.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_3), readTextFromFile("pr_serdca_3.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_4), readTextFromFile("pr_serdca_4.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_5), readTextFromFile("pr_serdca_5.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_6), readTextFromFile("pr_serdca_6.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_7), readTextFromFile("pr_serdca_7.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_8), readTextFromFile("pr_serdca_8.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_9), readTextFromFile("pr_serdca_9.txt")))
        kido.add(KidoSerdca(getString(R.string.pr_serdca_10), readTextFromFile("pr_serdca_10.txt")))

        val adapter = KidoSerdcaAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}