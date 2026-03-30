package com.vadym.gvd.bestfriendskotlin.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.fj.koreanlunarcalendar.KoreanLunarCalendarUtils
import com.vadym.gvd.bestfriendskotlin.FirebaseStorage
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.deviceLocale
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HeavenlyCalendarView : MainActivity() {

    private val storage = FirebaseStorage()
    private lateinit var recyclerView: RecyclerView
    private lateinit var titleMonthYear: TextView
    private lateinit var viewImportantDay: TextView
    private lateinit var importantDayPrev: TextView

    private val calendarDays = mutableListOf<CalendarDay>()

    private var displayYear  = Calendar.getInstance().get(Calendar.YEAR)
    private var displayMonth = Calendar.getInstance().get(Calendar.MONTH)
    private var importantDates = listOf<String>()

    private val anshiilReference: Long by lazy {
        Calendar.getInstance().apply {
            set(2004, Calendar.MAY, 5, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    data class CalendarDay(
        val gregorian: String,
        val month: Int,
        val year: Int,
        val lunar: String,
        val isToday: Boolean,
        val isAnshiil: Boolean,
        val isImportantDay: Boolean
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_calendar)

        bindViews()
        setupToolbar()
        setupBackPress()
        setupRecyclerView()
        setupNavigation()
        showTodayHeavenlyDate()

        // Єдиний виклик generateCalendar — тільки після завантаження дат
        storage.listHollyDaysFromFB { listDays ->
            importantDates = listDays.mapNotNull { it.day?.let(::splitAndGetGregorianDay) }
            checkImportantToday()
            generateCalendar()
        }
    }

    // ─── UI setup ────────────────────────────────────────────────────────────

    private fun bindViews() {
        titleMonthYear  = findViewById(R.id.title_month_year)
        recyclerView    = findViewById(R.id.recyclerViewCalendar)
        viewImportantDay = findViewById(R.id.view_important_day)
        importantDayPrev = findViewById(R.id.important_day_prev)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 7)
        recyclerView.adapter = CalendarAdapter(calendarDays) { _, position ->
            val day = calendarDays[position]
            val matchedIndex = importantDates.indexOfFirst {
                val c = it.importantCalendar()
                c.get(Calendar.MONTH) + 1 == day.month &&
                        c.get(Calendar.DAY_OF_MONTH).toString() == day.gregorian
            }
            if (matchedIndex >= 0) showImportantDay(matchedIndex) else hideImportantDay()
        }
    }

    private fun setupNavigation() {
        findViewById<ImageView>(R.id.prev_month).setOnClickListener {
            if (displayMonth == 0) { displayMonth = 11; displayYear-- } else displayMonth--
            generateCalendar()
        }
        findViewById<ImageView>(R.id.next_month).setOnClickListener {
            if (displayMonth == 11) { displayMonth = 0; displayYear++ } else displayMonth++
            generateCalendar()
        }
    }

    private fun showTodayHeavenlyDate() {
        val today = Calendar.getInstance()
        val lunar = KoreanLunarCalendarUtils.getLunarDateOf(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH) + 1,
            today.get(Calendar.DAY_OF_MONTH)
        )
        findViewById<TextView>(R.id.view_heavenly_date_today).text =
            calculateHeavenlyYear(lunar.lunMonth.toString(), lunar.lunDay.toString())
    }

    // ─── Calendar generation ──────────────────────────────────────────────────

    private fun generateCalendar() {
        val today = Calendar.getInstance()

        val iterCal = Calendar.getInstance().apply {
            set(displayYear, displayMonth, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        titleMonthYear.text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(iterCal.time)
        calendarDays.clear()

        // Порожні клітинки на початку тижня
        repeat(iterCal.get(Calendar.DAY_OF_WEEK) - 1) {
            calendarDays.add(CalendarDay("", -1, -1, "", false, false, false))
        }

        while (iterCal.get(Calendar.MONTH) == displayMonth) {
            val d = iterCal.get(Calendar.DAY_OF_MONTH)
            val m = iterCal.get(Calendar.MONTH) + 1
            val y = iterCal.get(Calendar.YEAR)
            val lunar = KoreanLunarCalendarUtils.getLunarDateOf(y, m, d)

            calendarDays.add(
                CalendarDay(
                    gregorian      = d.toString(),
                    month          = m,
                    year           = y,
                    lunar          = "(${lunar.lunMonth}-${lunar.lunDay})",
                    isToday        = d == today.get(Calendar.DAY_OF_MONTH) &&
                            iterCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                            y == today.get(Calendar.YEAR),
                    isAnshiil      = isAnshiilDay(iterCal),
                    isImportantDay = isImportantDay(iterCal)
                )
            )
            iterCal.add(Calendar.DAY_OF_MONTH, 1)
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }

    // ─── Day checks ───────────────────────────────────────────────────────────

    private fun isAnshiilDay(calendar: Calendar): Boolean {
        val midnight = Calendar.getInstance().apply {
            timeInMillis = calendar.timeInMillis
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0);      set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        return ((midnight - anshiilReference) / 86_400_000L).toInt() % 8 == 0
    }

    private fun isImportantDay(calendar: Calendar): Boolean =
        importantDates.any {
            val c = it.importantCalendar()
            calendar.get(Calendar.YEAR)         == c.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH)        == c.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)
        }

    // ─── Important day UI ─────────────────────────────────────────────────────

    private fun checkImportantToday() {
        val today = Calendar.getInstance()
        val idx = importantDates.indexOfFirst {
            val c = it.importantCalendar()
            c.get(Calendar.YEAR)        == today.get(Calendar.YEAR) &&
                    c.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
        }
        if (idx >= 0) showImportantDay(idx) else hideImportantDay()
    }

    private fun showImportantDay(index: Int) {
        val resId = resources.getIdentifier("day${index + 1}_title", "string", packageName)
        if (resId != 0) {
            viewImportantDay.text = resources.getString(resId)
            viewImportantDay.visibility = View.VISIBLE
            importantDayPrev.visibility = View.VISIBLE
        }
    }

    private fun hideImportantDay() {
        viewImportantDay.visibility = View.GONE
        importantDayPrev.visibility = View.GONE
    }

    // ─── Heavenly date calculation ────────────────────────────────────────────

    private fun calculateHeavenlyYear(month: String, day: String): String {
        val year = Calendar.getInstance().get(Calendar.YEAR) - 2012
        val anshiilText = if (isAnshiilDay(Calendar.getInstance()))
            ", ${getString(R.string.anshiil)}" else ""
        return getString(
            R.string.heavenly_date,
            getOrdinal(year),
            getOrdinal(month.toInt()),
            getOrdinal(day.toInt())
        ) + anshiilText
    }

    private fun getOrdinal(n: Int): String = when (n % 100) {
        11, 12, 13 -> getString(R.string.ordinal_other, n)
        else -> when (n % 10) {
            1    -> getString(R.string.ordinal_one,   n)
            2    -> getString(R.string.ordinal_two,   n)
            3    -> getString(R.string.ordinal_few,   n)
            else -> getString(R.string.ordinal_other, n)
        }
    }

    private fun splitAndGetGregorianDay(day: String): String {
        val parts = day.split(" ")
        return if (parts.size == 2) parts[1] else "0"
    }

    // ─── Navigation ───────────────────────────────────────────────────────────

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(MainActivity.EXTRA_OPEN_DRAWER, true)
            }
        )
        finish()
    }

    fun String.importantCalendar(): Calendar {
        val sdf = SimpleDateFormat("d/MM/yyyy", deviceLocale())
        val parsed = sdf.parse(this)
        return Calendar.getInstance().apply { time = parsed ?: Date() }
    }
}