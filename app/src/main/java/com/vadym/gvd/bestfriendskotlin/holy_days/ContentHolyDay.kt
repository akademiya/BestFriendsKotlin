package com.vadym.gvd.bestfriendskotlin.holy_days

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.FirebaseStorage
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R

class ContentHolyDay : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_holy_day_content)
        toolbarButtonMenu()
        val storage = FirebaseStorage(this)
        val sharedPreferences = getSharedPreferences("HollyDays", MODE_PRIVATE)
        val dayTitle = findViewById<TextView>(R.id.day_title)
        val celebrateHeavenlyDay = findViewById<TextView>(R.id.heavenly_day_of_celebrate)
        val celebrateGregorianDay = findViewById<TextView>(R.id.gregorian_day_of_celebrate)
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
                    val separateDays = splitDays(listDays.getOrNull(0)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day1_title)
                    dayDescription.text = resources.getString(R.string.day1_description)
                }

                2 -> {
                    val separateDays = splitDays(listDays.getOrNull(1)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day2_title)
                    dayDescription.text = resources.getString(R.string.day2_description)
                }

                3 -> {
                    val separateDays = splitDays(listDays.getOrNull(2)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day3_title)
                    dayDescription.text = resources.getString(R.string.day3_description)
                }

                4 -> {
                    val separateDays = splitDays(listDays.getOrNull(3)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day4_title)
                    dayDescription.text = resources.getString(R.string.day4_description)
                }

                5 -> {
                    val separateDays = splitDays(listDays.getOrNull(4)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day5_title)
                    dayDescription.text = resources.getString(R.string.day5_description)
                }

                6 -> {
                    val separateDays = splitDays(listDays.getOrNull(5)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day6_title)
                    dayDescription.text = resources.getString(R.string.day6_description)
                }

                7 -> {
                    val separateDays = splitDays(listDays.getOrNull(6)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day9_title)
                    dayDescription.text = resources.getString(R.string.day7_description)
                }

                8 -> {
                    val separateDays = splitDays(listDays.getOrNull(7)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day10_title)
                    dayDescription.text = resources.getString(R.string.day8_description)
                }

                9 -> {
                    val separateDays = splitDays(listDays.getOrNull(8)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day11_title)
                    dayDescription.text = resources.getString(R.string.day8_description)
                }

                10 -> {
                    val separateDays = splitDays(listDays.getOrNull(9)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day12_title)
                    dayDescription.text = resources.getString(R.string.day8_description)
                }

                11 -> {
                    val separateDays = splitDays(listDays.getOrNull(10)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day13_title)
                    dayDescription.text = resources.getString(R.string.day8_description)
                }

                12 -> {
                    val separateDays = splitDays(listDays.getOrNull(11)?.day?: "No data available")
                    val (heavenlyDay, gregorianDay) = separateDays
                    celebrateHeavenlyDay.text = heavenlyDay
                    celebrateGregorianDay.text = gregorianDay
                    dayTitle.text = resources.getString(R.string.day14_title)
                    dayDescription.text = resources.getString(R.string.day8_description)
                }

                else -> {
                    celebrateHeavenlyDay.text = "No data available"
                    celebrateGregorianDay.text = "No data available"
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

    private fun splitDays(day: String): Pair<String, String> {
        val parts = day.split(" ")
        return if (parts.size == 2) {
            val heavenlyDay = parts[0]
            val gregorianDay = parts[1]
            heavenlyDay to gregorianDay
        } else {
            "0" to "0"
        }
    }
}