package com.example.user.bestfriendskotlin.father_kido.intro

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R

class FatherKidoPobedyViewIntro : MainActivity() {

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
    }
}