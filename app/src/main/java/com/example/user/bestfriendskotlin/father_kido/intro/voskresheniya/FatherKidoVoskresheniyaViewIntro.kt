package com.example.user.bestfriendskotlin.father_kido.intro.voskresheniya

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

class FatherKidoVoskresheniyaViewIntro : MainActivity(), ReadTextFromFile  {

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

        tracker().setScreenName("Kido Voskresheniya")
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoVoskresheniya>()
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_1), readTextFromFile("pr_voskresheniya_1.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_2), readTextFromFile("pr_voskresheniya_2.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_3), readTextFromFile("pr_voskresheniya_3.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_4), readTextFromFile("pr_voskresheniya_4.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_5), readTextFromFile("pr_voskresheniya_5.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_6), readTextFromFile("pr_voskresheniya_6.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_7), readTextFromFile("pr_voskresheniya_7.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_8), readTextFromFile("pr_voskresheniya_8.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_9), readTextFromFile("pr_voskresheniya_9.txt")))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_10), readTextFromFile("pr_voskresheniya_10.txt")))

        val adapter = KidoVoskresheniyaAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}