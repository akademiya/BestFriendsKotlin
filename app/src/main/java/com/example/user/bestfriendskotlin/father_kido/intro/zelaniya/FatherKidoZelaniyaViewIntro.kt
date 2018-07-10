package com.example.user.bestfriendskotlin.father_kido.intro.zelaniya

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

class FatherKidoZelaniyaViewIntro : MainActivity(), ReadTextFromFile {

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

        val adapter = KidoZelaniyaAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}