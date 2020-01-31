package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders

class FragmentMenseRu : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mense_ru)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { startActivity(Intent(this, MainActivity::class.java).noAnimation()) }

        tracker().setScreenName("Fragment Mense Ru")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        tracker().send(HitBuilders.EventBuilder()
                .setCategory("Fragment Mense Ru")
                .build())
    }

    /** app bar menu переход в activity по клику на item */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ko -> startActivity(Intent(this, MenseView::class.java).noAnimation())
            R.id.en -> startActivity(Intent(this, FragmentMenseEn::class.java).noAnimation())
            R.id.ua -> startActivity(Intent(this, FragmentMenseUa::class.java).noAnimation())
            R.id.ru -> doNothing()
        }
        return super.onOptionsItemSelected(item)
    }
}