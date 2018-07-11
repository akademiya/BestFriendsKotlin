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
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_1), getString(R.string.pr_voskresheniya_1t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_2), getString(R.string.pr_voskresheniya_2t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_3), getString(R.string.pr_voskresheniya_3t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_4), getString(R.string.pr_voskresheniya_4t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_5), getString(R.string.pr_voskresheniya_5t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_6), getString(R.string.pr_voskresheniya_6t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_7), getString(R.string.pr_voskresheniya_7t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_8), getString(R.string.pr_voskresheniya_8t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_9), getString(R.string.pr_voskresheniya_9t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_10), getString(R.string.pr_voskresheniya_10t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_11), getString(R.string.pr_voskresheniya_11t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_12), getString(R.string.pr_voskresheniya_12t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_13), getString(R.string.pr_voskresheniya_13t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_14), getString(R.string.pr_voskresheniya_14t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_15), getString(R.string.pr_voskresheniya_15t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_16), getString(R.string.pr_voskresheniya_16t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_17), getString(R.string.pr_voskresheniya_17t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_18), getString(R.string.pr_voskresheniya_18t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_19), getString(R.string.pr_voskresheniya_19t)))
        kido.add(KidoVoskresheniya(getString(R.string.pr_voskresheniya_20), getString(R.string.pr_voskresheniya_20t)))

        val adapter = KidoVoskresheniyaAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}