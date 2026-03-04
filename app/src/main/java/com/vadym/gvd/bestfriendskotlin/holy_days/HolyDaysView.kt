package com.vadym.gvd.bestfriendskotlin.holy_days

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.FirebaseStorage
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R

class HolyDaysView : MainActivity() {

    private var count = 0
    private val listDays: MutableList<HolyDayEntity> = mutableListOf()
    private val storage = FirebaseStorage()

    // ✅ Єдине місце де описані всі святкові дні
    private fun buildItems(): List<HolyDayItem> = listOf(
        HolyDayItem(HolyDayEntity(), 0,  null,                  R.string.day1_title,  R.string.day1_description,  isMajor = true),
        HolyDayItem(HolyDayEntity(), 1,  null,                  R.string.day2_title,  R.string.day2_description,  isMajor = true),
        HolyDayItem(HolyDayEntity(), 2,  null,                  R.string.day3_title,  R.string.day3_description,  isMajor = true),
        HolyDayItem(HolyDayEntity(), 3,  null,                  R.string.day4_title,  R.string.day4_description,  isMajor = true),
        HolyDayItem(HolyDayEntity(), 4,  null,                  R.string.day5_title,  R.string.day5_description,  isMajor = true),
        HolyDayItem(HolyDayEntity(), 5,  null,                  R.string.day6_title,  R.string.day6_description,  isMajor = true),
        HolyDayItem(HolyDayEntity(), 6,  null,                  R.string.day7_title,  R.string.day7_description),
        HolyDayItem(HolyDayEntity(), 7,  null,                  R.string.day8_title,  R.string.day8_description),
        HolyDayItem(HolyDayEntity(), 8,  null,                  R.string.day9_title,  R.string.day9_description),
        HolyDayItem(HolyDayEntity(), 9,  null,                  R.string.day10_title, R.string.day10_description),
        HolyDayItem(HolyDayEntity(), 10, null,                  R.string.day11_title, R.string.day11_description),
        HolyDayItem(HolyDayEntity(), 11, null,                  R.string.day12_title, R.string.day12_description),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_holy_days)
        toolbarButtonMenu()

        storage.listHollyDaysFromFB { list ->
            listDays.addAll(list)
        }

        val items = buildItems()
        val rv = findViewById<RecyclerView>(R.id.rv_holy_days)
        rv.layoutManager = LinearLayoutManager(this)  // ✅ 1 колонка
        rv.adapter = HolyDaysAdapter(items) { item ->
            val intent = Intent(this, ContentHolyDay::class.java).apply {
                putExtra("HOLY_DAY_INDEX", item.index)
                putExtra("HOLY_DAY_TITLE_RES", item.titleRes)
                putExtra("HOLY_DAY_DESC_RES", item.descriptionRes)
            }
            startActivity(intent)
        }
    }

    private fun toolbarButtonMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { @Suppress("DEPRECATION") onBackPressed() }
        toolbar.setOnClickListener {
            if (count == 7) { updateCelebrationHollyDayDialog(); count = 0 } else count++
        }
    }

    private fun updateCelebrationHollyDayDialog() { /* без змін */ }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        })
        finish()
    }

}