package com.example.user.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders

class FragmentMenseUa : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mense_ua)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { _ -> startActivity(Intent(this, MainActivity::class.java)) }

        // analytics tracker
        tracker().setScreenName("FragmentMenseUa")
        tracker().send(HitBuilders.EventBuilder()
                .setCategory("Fragment Mense Ua")
                .build())
    }

    // TODO: app bar menu переход в activity по клику на item
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.ko -> startActivity(Intent(this, MenseView::class.java))
            R.id.en -> startActivity(Intent(this, FragmentMenseEn::class.java))
            R.id.ua -> doNothing()
            R.id.ru -> startActivity(Intent(this, FragmentMenseRu::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    // TODO: end app bar menu переход в activity по клику на item
}