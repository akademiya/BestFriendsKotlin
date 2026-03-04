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
import com.vadym.gvd.bestfriendskotlin.toHtml

class ContentHolyDay : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_holy_day_content)
        toolbarButtonMenu()

        val storage = FirebaseStorage()
        val dayTitle           = findViewById<TextView>(R.id.day_title)
        val celebrateHeavenly  = findViewById<TextView>(R.id.heavenly_day_of_celebrate)
        val celebrateGregorian = findViewById<TextView>(R.id.gregorian_day_of_celebrate)
        val dayDescription     = findViewById<TextView>(R.id.day_description)
        val adContainer        = findViewById<AdView>(R.id.adViewHollyDayContent)
        val adDivider          = findViewById<View>(R.id.adDivider)

        if (isNetworkAvailable()) {
            adContainer.visibility = View.VISIBLE
            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }

        // ✅ Отримуємо дані через Intent, без SharedPreferences
        val index   = intent.getIntExtra("HOLY_DAY_INDEX", 0)
        val titleRes = intent.getIntExtra("HOLY_DAY_TITLE_RES", R.string.day1_title)
        val descRes  = intent.getIntExtra("HOLY_DAY_DESC_RES", R.string.day1_description)

        dayTitle.text = getString(titleRes)
        dayDescription.text = getString(descRes).toHtml()

        storage.listHollyDaysFromFB { listDays ->
            val day = listDays.getOrNull(index)?.day ?: "No data available"
            val (heavenly, gregorian) = splitDays(day)
            celebrateHeavenly.text = heavenly
            celebrateGregorian.text = gregorian
        }
    }

    private fun splitDays(day: String): Pair<String, String> {
        val parts = day.split(" ")
        return if (parts.size == 2) parts[0] to parts[1] else "—" to "—"
    }

    private fun toolbarButtonMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { @Suppress("DEPRECATION") onBackPressed() }
    }
}