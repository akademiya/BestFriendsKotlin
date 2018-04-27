package com.example.user.bestfriendskotlin.father_kido

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.father_kido.intro.nadezdy.FatherKidoNadezdyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.pobedy.FatherKidoPobedyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.pochtitelnosty.FatherKidoPochtitelnostyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.serdca.FatherKidoSerdcaViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.voskresheniya.FatherKidoVoskresheniyaViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.zelaniya.FatherKidoZelaniyaViewIntro

class FatherKidoView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido)
        toolbar_button_menu()

        val rv = findViewById<RecyclerView>(R.id.view_list_tpkido)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.hasFixedSize()

        val books = ArrayList<FatherKido>()
        books.add(FatherKido(1, getString(R.string.pr_nadezdy)))
        books.add(FatherKido(2, getString(R.string.pr_pobedy)))
        books.add(FatherKido(3, getString(R.string.pr_serdca)))
        books.add(FatherKido(4, getString(R.string.pr_pochtitelnosty)))
        books.add(FatherKido(5, getString(R.string.pr_voskresheniya)))
        books.add(FatherKido(6, getString(R.string.pr_zelaniya)))

        val adapter = FatherKidoAdapter(books, { booksItem: FatherKido -> booksItemClicked(booksItem) })
        rv.adapter = adapter
    }

    private fun toolbar_button_menu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }
    }

    private fun booksItemClicked(booksItem: FatherKido) {
        when(booksItem.booksID) {
            1 -> startActivity(Intent(this, FatherKidoNadezdyViewIntro::class.java))
            2 -> startActivity(Intent(this, FatherKidoPobedyViewIntro::class.java))
            3 -> startActivity(Intent(this, FatherKidoSerdcaViewIntro::class.java))
            4 -> startActivity(Intent(this, FatherKidoPochtitelnostyViewIntro::class.java))
            5 -> startActivity(Intent(this, FatherKidoVoskresheniyaViewIntro::class.java))
            6 -> startActivity(Intent(this, FatherKidoZelaniyaViewIntro::class.java))
        }
    }
}