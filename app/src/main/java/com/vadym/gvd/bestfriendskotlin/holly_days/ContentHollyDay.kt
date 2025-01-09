package com.vadym.gvd.bestfriendskotlin.holly_days

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.FirebaseStorage
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R

class ContentHollyDay : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_holly_day_content)
        toolbarButtonMenu()
        val storage = FirebaseStorage(this)
        val sharedPreferences = getSharedPreferences("HollyDays", MODE_PRIVATE)
        val dayTitle = findViewById<TextView>(R.id.day_title)
        val celebrateDay = findViewById<TextView>(R.id.day_of_celebrate)
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
        storage.listHollyDaysFromFB { listDays ->
            when (fromView) {
                1 -> {
                    celebrateDay.text = listDays.getOrNull(0)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day1_title)
                    dayDescription.text = resources.getString(R.string.day1_description)
                }

                2 -> {
                    celebrateDay.text = listDays.getOrNull(1)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day2_title)
                    dayDescription.text = resources.getString(R.string.day2_description)
                }

                3 -> {
                    celebrateDay.text = listDays.getOrNull(2)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day3_title)
                    dayDescription.text = resources.getString(R.string.day3_description)
                }

                4 -> {
                    celebrateDay.text = listDays.getOrNull(3)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day4_title)
                    dayDescription.text = resources.getString(R.string.day4_description)
                }

                5 -> {
                    celebrateDay.text = listDays.getOrNull(4)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day5_title)
                    dayDescription.text = resources.getString(R.string.day5_description)
                }

                6 -> {
                    celebrateDay.text = listDays.getOrNull(5)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day6_title)
                    dayDescription.text = resources.getString(R.string.day6_description)
                }

                7 -> {
                    celebrateDay.text = listDays.getOrNull(6)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day7_title)
                    dayDescription.text = resources.getString(R.string.day7_description)
                }

                8 -> {
                    celebrateDay.text = listDays.getOrNull(7)?.day ?: "No data available"
                    dayTitle.text = resources.getString(R.string.day8_title)
                    dayDescription.text = resources.getString(R.string.day8_description)
                }

                else -> {
                    celebrateDay.text = "No data available"
                    dayTitle.text = resources.getString(R.string.day1_title)
                    dayDescription.text = resources.getString(R.string.day1_description)
                }
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