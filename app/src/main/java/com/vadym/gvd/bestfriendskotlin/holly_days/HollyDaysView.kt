package com.vadym.gvd.bestfriendskotlin.holly_days

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.noAnimation

class HollyDaysView : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_holly_days)
        toolbarButtonMenu()
        val sharedPreferences = getSharedPreferences("HollyDays", MODE_PRIVATE)

        val adContainer: AdView = findViewById(R.id.adViewHollyDays)
        val adDivider: View = findViewById(R.id.adDivider)

        val listDays = findViewById<LinearLayout>(R.id.list_days)

        for (child in listDays.children) {
            child.setOnClickListener { view ->
                when (view.id) {
                    R.id.card1 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 1).apply()
                    }
                    R.id.card2 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 2).apply()
                    }
                    R.id.card3 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 3).apply()
                    }
                    R.id.card4 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 4).apply()
                    }
                    R.id.card5 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 5).apply()
                    }
                    R.id.card6 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 6).apply()
                    }
                    R.id.card7 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 7).apply()
                    }
                    R.id.card8 -> {
                        startActivity(Intent(this, ContentHollyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 8).apply()
                    }
                    else -> Toast.makeText(this, "Unknown item clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }


        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }
    }

    private fun toolbarButtonMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }
}