package com.example.user.bestfriendskotlin.father_kido.intro

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import kotlinx.android.synthetic.main.view_father_kido_nadezdy.*

class FatherKidoNadezdyViewIntro : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_father_kido_nadezdy)

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
        kido_title.text = getString(R.string.pr_nadezdy_1)
        kido_description.text = getString(R.string.pr_nadezdy_1d)

        kido_title1.text = getString(R.string.pr_nadezdy_2)
        kido_description1.text = getString(R.string.pr_nadezdy_2d)
    }
}