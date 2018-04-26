package com.example.user.bestfriendskotlin.father_kido.intro.zelaniya

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.father_kido.intro.ReadTextFromFile
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

        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoZelaniya>()
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_1), readTextFromFile("pr_zelaniya_1.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_2), readTextFromFile("pr_zelaniya_2.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_3), readTextFromFile("pr_zelaniya_3.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_4), readTextFromFile("pr_zelaniya_4.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_5), readTextFromFile("pr_zelaniya_5.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_6), readTextFromFile("pr_zelaniya_6.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_7), readTextFromFile("pr_zelaniya_7.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_8), readTextFromFile("pr_zelaniya_8.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_9), readTextFromFile("pr_zelaniya_9.txt")))
        kido.add(KidoZelaniya(getString(R.string.pr_zelaniya_10), readTextFromFile("pr_zelaniya_10.txt")))

        val adapter = KidoZelaniyaAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}