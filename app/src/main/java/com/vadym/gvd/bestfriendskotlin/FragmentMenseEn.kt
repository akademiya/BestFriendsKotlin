package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders

class FragmentMenseEn : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mense_en)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { startActivity(Intent(this, MainActivity::class.java).noAnimation()) }
    }

    /** app bar menu переход в activity по клику на item */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ko -> startActivity(Intent(this, FragmentMenseKo::class.java).noAnimation())
            R.id.en -> startActivity(Intent(this, FragmentMenseEn::class.java).noAnimation())
            R.id.ua -> startActivity(Intent(this, FragmentMenseUa::class.java).noAnimation())
            R.id.ru -> startActivity(Intent(this, FragmentMenseRu::class.java).noAnimation())
        }
        return super.onOptionsItemSelected(item)
    }
}