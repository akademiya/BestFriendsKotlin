package com.vadym.gvd.bestfriendskotlin.holy_days

import android.app.AlertDialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.Admob
import com.vadym.gvd.bestfriendskotlin.FirebaseStorage
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.noAnimation

class HolyDaysView : MainActivity() {

    private var count = 0
    private val listDays: MutableList<HolyDayEntity> = mutableListOf()
    private val storage = FirebaseStorage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_holy_days)
        toolbarButtonMenu()
        val sharedPreferences = getSharedPreferences("HollyDays", MODE_PRIVATE)

        val adContainer: AdView = findViewById(R.id.adViewHollyDays)
        val adDivider: View = findViewById(R.id.adDivider)

        storage.listHollyDaysFromFB { list ->
            for (item in list) {
                listDays.add(item)
            }
        }

        val listDaysView = findViewById<LinearLayout>(R.id.list_days)
        for (child in listDaysView.children) {
            child.setOnClickListener { view ->
                when (view.id) {
                    R.id.card1 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 1).apply()
                    }

                    R.id.card2 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 2).apply()
                    }

                    R.id.card3 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 3).apply()
                    }

                    R.id.card4 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 4).apply()
                    }

                    R.id.card5 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 5).apply()
                    }

                    R.id.card6 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 6).apply()
                    }

                    R.id.card7 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 7).apply()
                    }

                    R.id.card8 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 8).apply()
                    }

                    R.id.card9 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 9).apply()
                    }

                    R.id.card10 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 10).apply()
                    }

                    R.id.card11 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 11).apply()
                    }

                    R.id.card12 -> {
                        startActivity(Intent(this, ContentHolyDay::class.java).noAnimation())
                        sharedPreferences.edit().putInt("card", 12).apply()
                    }

                    else -> Toast.makeText(this, "Unknown item clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }


        if (isNetworkAvailable()) {
            adContainer.visibility = View.VISIBLE
            adDivider.visibility = View.VISIBLE
            Admob.initializeAdmob(this, adContainer)
        } else {
            adContainer.visibility = View.GONE
            adDivider.visibility = View.GONE
        }

        /** Don`t remove this method */
//        createListAndSave(storage)
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

        toolbar.setOnClickListener {
            if (count == 7) {
                updateCelebrationHollyDayDialog()
                count = 0
            } else count++
        }
    }

    private fun updateCelebrationHollyDayDialog() {
        val inflater = LayoutInflater.from(this)
        val subView = inflater.inflate(R.layout.dialog_change_celebration_day, null)

        val editTexts = listOf<EditText>(
            subView.findViewById(R.id.day1),
            subView.findViewById(R.id.day2),
            subView.findViewById(R.id.day3),
            subView.findViewById(R.id.day4),
            subView.findViewById(R.id.day5),
            subView.findViewById(R.id.day6),
            subView.findViewById(R.id.day7),
            subView.findViewById(R.id.day8),
            subView.findViewById(R.id.day9),
            subView.findViewById(R.id.day10),
            subView.findViewById(R.id.day11),
            subView.findViewById(R.id.day12)
        )

        editTexts.forEachIndexed { index, editText ->
            editText.apply {
                background.setColorFilter(
                    resources.getColor(R.color.color_text, null),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change celebration day dialog")
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton("Update") { _, _ ->
            editTexts.forEachIndexed { index, editText ->
                editText.apply {
                    if (text.isEmpty()) return@apply
                    listDays[index].day = text.toString()
                }
            }
            updateValueOfDay(storage, listDays)
        }
        builder.show()
    }

    private fun updateValueOfDay(storage: FirebaseStorage, list: List<HolyDayEntity>) {
        for (item in list) {
            storage.updateValueOfDay(item.id.toString(), item.day.toString())
        }
    }


    /** Коли потрібно глобально очистити БД, та закачати нові дані звідси */
    private fun createListAndSave(storage: FirebaseStorage) {
        val listDays = mutableListOf(
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day1_title),
                day = "16/03/13 13/04/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day2_title),
                day = "01/05/13 20/11/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day3_title),
                day = "01/07/13 23/08/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day4_title),
                day = "07/07/13 29/08/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day5_title),
                day = "01/10/13 20/11/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day6_title),
                day = "03/10/13 22/11/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day7_title),
                day = "01/01/13 29/01/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day8_title),
                day = "06/01/13 03/02/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day9_title),
                day = "13/01/13 10/02/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day10_title),
                day = "01/05/13 27/05/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day11_title),
                day = "13/06/13 07/07/2025"
            ),
            HolyDayEntity(
                id = "id",
                title = resources.getString(R.string.day12_title),
                day = "17/07/13 08/09/2025"
            )
        )

        storage.saveDaysToFirebase(listDays)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(MainActivity.EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }

}