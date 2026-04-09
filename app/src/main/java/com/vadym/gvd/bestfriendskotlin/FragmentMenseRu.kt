package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity.Companion.EXTRA_OPEN_DRAWER

class FragmentMenseRu : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mense_ru)
        setupBackPress()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { navigateBack() }
    }

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
            R.id.deviz -> startActivity(Intent(this, FragmentDeviz::class.java).noAnimation())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }
}