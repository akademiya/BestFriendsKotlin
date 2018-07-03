package com.example.user.bestfriendskotlin

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.view_info.*

class DonationView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_info)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

//        val button_menu: ImageView = findViewById(R.id.button_menu)
//        button_menu.setOnClickListener { _ -> drawer_layout.openDrawer(Gravity.START) }

        donation.setOnClickListener { Toast.makeText(applicationContext, "click ok", Toast.LENGTH_SHORT).show() }

    }

}