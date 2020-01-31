package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders

class MenseView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kajon_mense)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }

        tracker().setScreenName("Mense")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
    }

    /** app bar menu переход в activity по клику на item */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ko -> doNothing()
            R.id.en -> startActivity(Intent(this, FragmentMenseEn::class.java).noAnimation())
            R.id.ua -> startActivity(Intent(this, FragmentMenseUa::class.java).noAnimation())
            R.id.ru -> startActivity(Intent(this, FragmentMenseRu::class.java).noAnimation())
        }
        return super.onOptionsItemSelected(item)
    }
}