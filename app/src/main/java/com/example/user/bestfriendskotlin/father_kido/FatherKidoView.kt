package com.example.user.bestfriendskotlin.father_kido

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.father_kido.intro.faith.FatherKidoFaithViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.loyalty.FatherKidoLoyaltyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.nadezdy.FatherKidoNadezdyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.pobedy.FatherKidoPobedyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.pochtitelnosty.FatherKidoPochtitelnostyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.reshimosty.FatherKidoReshimostyViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.restoration.FatherKidoRestorationViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.serdca.FatherKidoSerdcaViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.voskresheniya.FatherKidoVoskresheniyaViewIntro
import com.example.user.bestfriendskotlin.father_kido.intro.zelaniya.FatherKidoZelaniyaViewIntro
import com.example.user.bestfriendskotlin.tracker
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.view_father_kido.*


class FatherKidoView : MainActivity() {
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido)
        toolbar_button_menu()
        admob()

        view_list_tpkido.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        view_list_tpkido.hasFixedSize()

        val books = ArrayList<FatherKido>()
        books.add(FatherKido(1, getString(R.string.pr_nadezdy)))
        books.add(FatherKido(2, getString(R.string.pr_pobedy)))
        books.add(FatherKido(3, getString(R.string.pr_serdca)))
        books.add(FatherKido(4, getString(R.string.pr_pochtitelnosty)))
        books.add(FatherKido(5, getString(R.string.pr_voskresheniya)))
        books.add(FatherKido(6, getString(R.string.pr_zelaniya)))
        books.add(FatherKido(7, getString(R.string.pr_reshimosty)))
        books.add(FatherKido(8, getString(R.string.pr_faith)))
        books.add(FatherKido(9, getString(R.string.pr_loyalty)))
        books.add(FatherKido(10, getString(R.string.pr_restoration)))

        val adapter = FatherKidoAdapter(books, { booksItem: FatherKido -> booksItemClicked(booksItem) })
        view_list_tpkido.adapter = adapter
    }

    private fun admob() {
        // Sample AdMob app ID: ca-app-pub-5169531562006723/6552139134
        MobileAds.initialize(this, "ca-app-pub-5169531562006723~5810328137")

        val adView = AdView(this)
        adView.adSize = AdSize.SMART_BANNER
        adView.adUnitId = "ca-app-pub-5169531562006723/6552139134"

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun toolbar_button_menu() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }
    }

    private fun booksItemClicked(booksItem: FatherKido) {

        // analytics tracker
        tracker().send(HitBuilders.EventBuilder()
                .setCategory("Father Kido")
                .setAction("List")
                .build())

        when(booksItem.booksID) {
            1 -> startActivity(Intent(this, FatherKidoNadezdyViewIntro::class.java))
            2 -> startActivity(Intent(this, FatherKidoPobedyViewIntro::class.java))
            3 -> startActivity(Intent(this, FatherKidoSerdcaViewIntro::class.java))
            4 -> startActivity(Intent(this, FatherKidoPochtitelnostyViewIntro::class.java))
            5 -> startActivity(Intent(this, FatherKidoVoskresheniyaViewIntro::class.java))
            6 -> startActivity(Intent(this, FatherKidoZelaniyaViewIntro::class.java))
            7 -> startActivity(Intent(this, FatherKidoReshimostyViewIntro::class.java))
            8 -> startActivity(Intent(this, FatherKidoFaithViewIntro::class.java))
            9 -> startActivity(Intent(this, FatherKidoLoyaltyViewIntro::class.java))
            10 -> startActivity(Intent(this, FatherKidoRestorationViewIntro::class.java))
        }
    }
}