package com.vadym.gvd.bestfriendskotlin.holly_days

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R

class ContentHollyDay : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_holly_day_content)
        toolbarButtonMenu()
        val sharedPreferences = getSharedPreferences("HollyDays", MODE_PRIVATE)
        val dayTitle = findViewById<TextView>(R.id.day_title)
        val dayDescription = findViewById<TextView>(R.id.day_description)

        val adContainer: AdView = findViewById(R.id.adViewHollyDayContent)
        val adDivider: View = findViewById(R.id.adDivider)

        if (isNetworkAvailable(this)) {
            adContainer.visibility = View.VISIBLE
            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }

        val fromView = sharedPreferences.getInt("card", 1)
        when (fromView) {
            1 -> {
                dayTitle.text = resources.getString(R.string.day1_title)
                dayDescription.text = resources.getString(R.string.day1_description)
            }
            2 -> {
                dayTitle.text = resources.getString(R.string.day2_title)
                dayDescription.text = resources.getString(R.string.day2_description)
            }
            3 -> {
                dayTitle.text = resources.getString(R.string.day3_title)
                dayDescription.text = resources.getString(R.string.day3_description)
            }
            4 -> {
                dayTitle.text = resources.getString(R.string.day4_title)
                dayDescription.text = resources.getString(R.string.day4_description)
            }
            5 -> {
                dayTitle.text = resources.getString(R.string.day5_title)
                dayDescription.text = resources.getString(R.string.day5_description)
            }
            6 -> {
                dayTitle.text = resources.getString(R.string.day6_title)
                dayDescription.text = resources.getString(R.string.day6_description)
            }
            7 -> {
                dayTitle.text = resources.getString(R.string.day7_title)
                dayDescription.text = resources.getString(R.string.day7_description)
            }
            8 -> {
                dayTitle.text = resources.getString(R.string.day8_title)
                dayDescription.text = resources.getString(R.string.day8_description)
            }
            else -> {
                dayTitle.text = resources.getString(R.string.day1_title)
                dayDescription.text = resources.getString(R.string.day1_description)
            }
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