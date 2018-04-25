package com.example.user.bestfriendskotlin.father_kido.intro.nadezdy

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.father_kido.intro.ReadTextFromFile
import java.io.InputStream
import java.util.ArrayList

class FatherKidoNadezdyViewIntro : MainActivity(), ReadTextFromFile {

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

        val kido = ArrayList<KidoNadezdy>()
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_1), readTextFromFile("pr_nadezdy_1.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_2), readTextFromFile("pr_nadezdy_2.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_3), readTextFromFile("pr_nadezdy_3.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_4), readTextFromFile("pr_nadezdy_4.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_5), readTextFromFile("pr_nadezdy_5.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_6), readTextFromFile("pr_nadezdy_6.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_7), readTextFromFile("pr_nadezdy_7.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_8), readTextFromFile("pr_nadezdy_8.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_9), readTextFromFile("pr_nadezdy_9.txt")))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_10), readTextFromFile("pr_nadezdy_10.txt")))

        val adapter = KidoNadezdyAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}