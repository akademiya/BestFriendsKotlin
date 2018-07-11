package com.example.user.bestfriendskotlin.father_kido.intro.pobedy

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

class FatherKidoPobedyViewIntro : MainActivity(), ReadTextFromFile {

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

        tracker().setScreenName("Kido Pobedy")
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoPobedy>()
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_1), getString(R.string.pr_pobedy_1t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_2), getString(R.string.pr_pobedy_2t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_3), getString(R.string.pr_pobedy_3t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_4), getString(R.string.pr_pobedy_4t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_5), getString(R.string.pr_pobedy_5t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_6), getString(R.string.pr_pobedy_6t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_7), getString(R.string.pr_pobedy_7t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_8), getString(R.string.pr_pobedy_8t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_9), getString(R.string.pr_pobedy_9t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_10), getString(R.string.pr_pobedy_10t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_11), getString(R.string.pr_pobedy_11t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_12), getString(R.string.pr_pobedy_12t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_13), getString(R.string.pr_pobedy_13t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_14), getString(R.string.pr_pobedy_14t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_15), getString(R.string.pr_pobedy_15t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_16), getString(R.string.pr_pobedy_16t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_17), getString(R.string.pr_pobedy_17t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_18), getString(R.string.pr_pobedy_18t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_19), getString(R.string.pr_pobedy_19t)))
        kido.add(KidoPobedy(getString(R.string.pr_pobedy_20), getString(R.string.pr_pobedy_20t)))

        val adapter = KidoPobedyAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}