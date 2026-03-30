package com.vadym.gvd.bestfriendskotlin.father_kido

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamingan.KidoChaminganView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamkajon.KidoChamkajonView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chammanmul.KidoChammanmulView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.champumo.KidoChampumoView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamsaran.KidoChamsaranView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.chonilguk.KidoChonilgukView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.devotion.FatherKidoDevotionViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.faith.FatherKidoFaithViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.hananim.KidoHananimView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.loyalty.FatherKidoLoyaltyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.menjol.KidoMenjolView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.mesia.KidoMesiaView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.nadezdy.FatherKidoNadezdyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.penhwamesigi.KidoPenhwamesigiView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.penhwasasan.KidoPenhwasasanView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.pobedy.FatherKidoPobedyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.pochtitelnosty.FatherKidoPochtitelnostyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.reshimosty.FatherKidoReshimostyViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.restoration.FatherKidoRestorationViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.serdca.FatherKidoSerdcaViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.suren.KidoSurenView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.unification.FatherKidoUnificationViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.voskresheniya.FatherKidoVoskresheniyaViewIntro
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.yongye.KidoYongyeView
import com.vadym.gvd.bestfriendskotlin.father_kido.intro.zelaniya.FatherKidoZelaniyaViewIntro


class FatherKidoView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido)
        setupToolbar()
        setupBackPress()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun setupRecyclerView() {
        val books = buildBooksList()
        val recyclerView = findViewById<RecyclerView>(R.id.view_list_tpkido)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = FatherKidoAdapter(books) { booksItemClicked(it) }
    }


    private fun buildBooksList() = listOf(
        FatherKido(1,  getString(R.string.pr_nadezdy)),
        FatherKido(2,  getString(R.string.pr_zelaniya)),
        FatherKido(3,  getString(R.string.pr_voskresheniya)),
        FatherKido(4,  getString(R.string.pr_serdca)),
        FatherKido(5,  getString(R.string.pr_faith)),
        FatherKido(6,  getString(R.string.pr_pochtitelnosty)),
        FatherKido(7,  getString(R.string.pr_loyalty)),
        FatherKido(8,  getString(R.string.pr_reshimosty)),
        FatherKido(9,  getString(R.string.pr_devotion)),
        FatherKido(10, getString(R.string.pr_restoration)),
        FatherKido(11, getString(R.string.pr_pobedy)),
        FatherKido(12, getString(R.string.pr_unification)),
        FatherKido(13, getString(R.string.b_hananim)),
        FatherKido(14, getString(R.string.b_champumo)),
        FatherKido(15, getString(R.string.b_chamsaran)),
        FatherKido(16, getString(R.string.b_chamingan)),
        FatherKido(17, getString(R.string.b_chamkajon)),
        FatherKido(18, getString(R.string.b_chammanmul)),
        FatherKido(19, getString(R.string.b_yongye)),
        FatherKido(20, getString(R.string.b_suren)),
        FatherKido(21, getString(R.string.b_mesia)),
        FatherKido(22, getString(R.string.b_penhwasasan)),
        FatherKido(23, getString(R.string.b_menjol)),
        FatherKido(24, getString(R.string.b_chonilguk)),
        FatherKido(25, getString(R.string.b_penhwamesigi)),
    )

    private val destinations: Map<Int, Class<*>> = mapOf(
        1  to FatherKidoNadezdyViewIntro::class.java,
        2  to FatherKidoZelaniyaViewIntro::class.java,
        3  to FatherKidoVoskresheniyaViewIntro::class.java,
        4  to FatherKidoSerdcaViewIntro::class.java,
        5  to FatherKidoFaithViewIntro::class.java,
        6  to FatherKidoPochtitelnostyViewIntro::class.java,
        7  to FatherKidoLoyaltyViewIntro::class.java,
        8  to FatherKidoReshimostyViewIntro::class.java,
        9  to FatherKidoDevotionViewIntro::class.java,
        10 to FatherKidoRestorationViewIntro::class.java,
        11 to FatherKidoPobedyViewIntro::class.java,
        12 to FatherKidoUnificationViewIntro::class.java,
        13 to KidoHananimView::class.java,
        14 to KidoChampumoView::class.java,
        15 to KidoChamsaranView::class.java,
        16 to KidoChaminganView::class.java,
        17 to KidoChamkajonView::class.java,
        18 to KidoChammanmulView::class.java,
        19 to KidoYongyeView::class.java,
        20 to KidoSurenView::class.java,
        21 to KidoMesiaView::class.java,
        22 to KidoPenhwasasanView::class.java,
        23 to KidoMenjolView::class.java,
        24 to KidoChonilgukView::class.java,
        25 to KidoPenhwamesigiView::class.java,
    )

    private fun booksItemClicked(item: FatherKido) {
        destinations[item.booksID]?.let { startActivity(Intent(this, it)) }
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        })
        finish()
    }
}