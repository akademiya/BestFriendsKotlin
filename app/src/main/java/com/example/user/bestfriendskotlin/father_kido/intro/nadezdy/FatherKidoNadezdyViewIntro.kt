package com.example.user.bestfriendskotlin.father_kido.intro.nadezdy

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

        tracker().setScreenName("Kido Nadezdy")
        init()
    }

    private fun init() {
        val rv = findViewById<RecyclerView>(R.id.rv_list_father_kido_intro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val kido = ArrayList<KidoNadezdy>()
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_1), getString(R.string.pr_nadezdy_1t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_2), getString(R.string.pr_nadezdy_2t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_3), getString(R.string.pr_nadezdy_3t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_4), getString(R.string.pr_nadezdy_4t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_5), getString(R.string.pr_nadezdy_5t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_6), getString(R.string.pr_nadezdy_6t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_7), getString(R.string.pr_nadezdy_7t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_8), getString(R.string.pr_nadezdy_8t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_9), getString(R.string.pr_nadezdy_9t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_10), getString(R.string.pr_nadezdy_10t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_11), getString(R.string.pr_nadezdy_11t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_12), getString(R.string.pr_nadezdy_12t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_13), getString(R.string.pr_nadezdy_13t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_14), getString(R.string.pr_nadezdy_14t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_15), getString(R.string.pr_nadezdy_15t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_16), getString(R.string.pr_nadezdy_16t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_17), getString(R.string.pr_nadezdy_17t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_18), getString(R.string.pr_nadezdy_18t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_19), getString(R.string.pr_nadezdy_19t)))
        kido.add(KidoNadezdy(getString(R.string.pr_nadezdy_20), getString(R.string.pr_nadezdy_20t)))

        val adapter = KidoNadezdyAdapter(kido)
        rv.adapter = adapter
    }

    override fun readTextFromFile(textFile: String): String {
        val inputStream: InputStream = assets.open(textFile)
        return inputStream.bufferedReader().use { it.readText() }
    }
}