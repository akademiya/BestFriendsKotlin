package com.vadym.gvd.bestfriendskotlin.father_kido

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.devotion.FatherKidoDevotionViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.faith.FatherKidoFaithViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.loyalty.FatherKidoLoyaltyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.nadezdy.FatherKidoNadezdyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.pobedy.FatherKidoPobedyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.pochtitelnosty.FatherKidoPochtitelnostyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.reshimosty.FatherKidoReshimostyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.restoration.FatherKidoRestorationViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.serdca.FatherKidoSerdcaViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.unification.FatherKidoUnificationViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.voskresheniya.FatherKidoVoskresheniyaViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.zelaniya.FatherKidoZelaniyaViewIntro
import com.vadym.gvd.bestfriendskotlin.tracker
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamingan.KidoChaminganView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamkajon.KidoChamkajonView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chammanmul.KidoChammanmulView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.champumo.KidoChampumoView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamsaran.KidoChamsaranView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chonilguk.KidoChonilgukView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.hananim.KidoHananimView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.menjol.KidoMenjolView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.mesia.KidoMesiaView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.penhwamesigi.KidoPenhwamesigiView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.penhwasasan.KidoPenhwasasanView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.suren.KidoSurenView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.yongye.KidoYongyeView
import kotlinx.android.synthetic.main.view_father_kido.*


class FatherKidoView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido)
        toolbarButtonMenu()
        admob()
        tracker().setScreenName("Father Kido")
        tracker().send(HitBuilders.ScreenViewBuilder().build())

        view_list_tpkido.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        view_list_tpkido.hasFixedSize()

        val books = ArrayList<FatherKido>()
        books.add(FatherKido(1, getString(R.string.pr_nadezdy)))
        books.add(FatherKido(2, getString(R.string.pr_zelaniya)))
        books.add(FatherKido(3, getString(R.string.pr_voskresheniya)))
        books.add(FatherKido(4, getString(R.string.pr_serdca)))
        books.add(FatherKido(5, getString(R.string.pr_faith)))
        books.add(FatherKido(6, getString(R.string.pr_pochtitelnosty)))
        books.add(FatherKido(7, getString(R.string.pr_loyalty)))
        books.add(FatherKido(8, getString(R.string.pr_reshimosty)))
        books.add(FatherKido(9, getString(R.string.pr_devotion)))
        books.add(FatherKido(10, getString(R.string.pr_restoration)))
        books.add(FatherKido(11, getString(R.string.pr_pobedy)))
        books.add(FatherKido(12, getString(R.string.pr_unification)))

        books.add(FatherKido(13, getString(R.string.b_hananim)))
        books.add(FatherKido(14, getString(R.string.b_champumo)))
        books.add(FatherKido(15, getString(R.string.b_chamsaran)))
        books.add(FatherKido(16, getString(R.string.b_chamingan)))
        books.add(FatherKido(17, getString(R.string.b_chamkajon)))
        books.add(FatherKido(18, getString(R.string.b_chammanmul)))
        books.add(FatherKido(19, getString(R.string.b_yongye)))
        books.add(FatherKido(20, getString(R.string.b_suren)))
        books.add(FatherKido(21, getString(R.string.b_mesia)))
        books.add(FatherKido(22, getString(R.string.b_penhwasasan)))
        books.add(FatherKido(23, getString(R.string.b_menjol)))
        books.add(FatherKido(24, getString(R.string.b_chonilguk)))
        books.add(FatherKido(25, getString(R.string.b_penhwamesigi)))

        val adapter = FatherKidoAdapter(books) { booksItem: FatherKido -> booksItemClicked(booksItem) }
        view_list_tpkido.adapter = adapter
    }

    private fun admob() {
        MobileAds.initialize(this, "ca-app-pub-5169531562006723~5810328137")

        val gAdView = AdView(this)
        gAdView.adSize = AdSize.SMART_BANNER
        gAdView.adUnitId = "ca-app-pub-5169531562006723/6552139134"

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun toolbarButtonMenu() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun booksItemClicked(booksItem: FatherKido) {

        tracker().send(HitBuilders.EventBuilder()
                .setCategory("Father Kido")
                .setAction(booksItem.booksID.toString())
                .build())

        when(booksItem.booksID) {
            1 -> startActivity(Intent(this, FatherKidoNadezdyViewIntro::class.java))
            2 -> startActivity(Intent(this, FatherKidoZelaniyaViewIntro::class.java))
            3 -> startActivity(Intent(this, FatherKidoVoskresheniyaViewIntro::class.java))
            4 -> startActivity(Intent(this, FatherKidoSerdcaViewIntro::class.java))
            5 -> startActivity(Intent(this, FatherKidoFaithViewIntro::class.java))
            6 -> startActivity(Intent(this, FatherKidoPochtitelnostyViewIntro::class.java))
            7 -> startActivity(Intent(this, FatherKidoLoyaltyViewIntro::class.java))
            8 -> startActivity(Intent(this, FatherKidoReshimostyViewIntro::class.java))
            9 -> startActivity(Intent(this, FatherKidoDevotionViewIntro::class.java))
            10 -> startActivity(Intent(this, FatherKidoRestorationViewIntro::class.java))
            11 -> startActivity(Intent(this, FatherKidoPobedyViewIntro::class.java))
            12 -> startActivity(Intent(this, FatherKidoUnificationViewIntro::class.java))
            13 -> startActivity(Intent(this, KidoHananimView::class.java))
            14 -> startActivity(Intent(this, KidoChampumoView::class.java))
            15 -> startActivity(Intent(this, KidoChamsaranView::class.java))
            16 -> startActivity(Intent(this, KidoChaminganView::class.java))
            17 -> startActivity(Intent(this, KidoChamkajonView::class.java))
            18 -> startActivity(Intent(this, KidoChammanmulView::class.java))
            19 -> startActivity(Intent(this, KidoYongyeView::class.java))
            20 -> startActivity(Intent(this, KidoSurenView::class.java))
            21 -> startActivity(Intent(this, KidoMesiaView::class.java))
            22 -> startActivity(Intent(this, KidoPenhwasasanView::class.java))
            23 -> startActivity(Intent(this, KidoMenjolView::class.java))
            24 -> startActivity(Intent(this, KidoChonilgukView::class.java))
            25 -> startActivity(Intent(this, KidoPenhwamesigiView::class.java))
        }
    }
}