package com.vadym.gvd.bestfriendskotlin.calendar

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.ChineseCalendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.fj.koreanlunarcalendar.KoreanLunarCalendarUtils
import com.vadym.gvd.bestfriendskotlin.FirebaseStorage
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.importantCalendar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val NOTIFICATION_CHANNEL_ID = "HeavenlyCalendarChannel"

class HeavenlyCalendarView : MainActivity() {

    private val storage = FirebaseStorage(this)
    private lateinit var recyclerView: RecyclerView
    private lateinit var titleMonthYear: TextView
    private lateinit var viewImportantDay: TextView
    private lateinit var importantDayPrev: TextView

    private val calendarDays = mutableListOf<CalendarDay>()
    private val adapter by lazy { recyclerView.adapter as CalendarAdapter }

    // ✅ Зберігаємо тільки рік+місяць для навігації, не мутуємо між рендерами
    private var displayYear: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var displayMonth: Int = Calendar.getInstance().get(Calendar.MONTH)

    private var importantDates = listOf<String>()

    // ✅ Кешуємо еталонну точку один раз
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

        titleMonthYear = findViewById(R.id.title_month_year)
        recyclerView = findViewById(R.id.recyclerViewCalendar)
        viewImportantDay = findViewById(R.id.view_important_day)
        importantDayPrev = findViewById(R.id.important_day_prev)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val viewHeavenlyDateToday = findViewById<TextView>(R.id.view_heavenly_date_today)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setupRecyclerView()

        storage.listHollyDaysFromFB { listDays ->
            importantDates = listDays.mapNotNull { it.day?.let(::splitAndGetGregorianDay) }
            checkImportantToday()
            generateCalendar()
        }

        generateCalendar()
        createNotificationChannel()
        scheduleNotification()

        // ✅ Виправлені кнопки навігації — змінюємо displayMonth, не мутуємо Calendar
        findViewById<ImageView>(R.id.prev_month).setOnClickListener {
            if (displayMonth == 0) { displayMonth = 11; displayYear-- }
            else displayMonth--
            generateCalendar()
        }

        findViewById<ImageView>(R.id.next_month).setOnClickListener {
            if (displayMonth == 11) { displayMonth = 0; displayYear++ }
            else displayMonth++
            generateCalendar()
        }

        val today = Calendar.getInstance()
        val lunarToday = KoreanLunarCalendarUtils.getLunarDateOf(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH) + 1,
            today.get(Calendar.DAY_OF_MONTH)
        )
        viewHeavenlyDateToday.text = calculateHeavenlyYear(
            lunarToday.lunMonth.toString(),
            lunarToday.lunDay.toString()
        )
    }

    private fun setupRecyclerView() {
        val calendarAdapter = CalendarAdapter(calendarDays) { _, position ->
            val day = calendarDays[position]
            val matched = importantDates.find {
                val c = it.importantCalendar()
                c.get(Calendar.MONTH) + 1 == day.month &&
                        c.get(Calendar.DAY_OF_MONTH).toString() == day.gregorian
            }
            if (matched != null) {
                val idx = importantDates.indexOf(matched)
                showImportantDay(idx)
            } else {
                hideImportantDay()
            }
        }
        recyclerView.layoutManager = GridLayoutManager(this, 7)
        recyclerView.adapter = calendarAdapter
    }

    private fun generateCalendar() {
        val today = Calendar.getInstance()
        val todayDay   = today.get(Calendar.DAY_OF_MONTH)
        val todayMonth = today.get(Calendar.MONTH)
        val todayYear  = today.get(Calendar.YEAR)

        // ✅ Створюємо свіжий Calendar для ітерації — не чіпаємо поле класу
        val iterCal = Calendar.getInstance().apply {
            set(displayYear, displayMonth, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        titleMonthYear.text = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            .format(iterCal.time)

        calendarDays.clear()

        // Порожні клітинки на початку
        val firstDow = iterCal.get(Calendar.DAY_OF_WEEK)
        repeat(firstDow - 1) {
            calendarDays.add(CalendarDay("", -1, -1, "", false, false, false))
        }

        val dateFormat = SimpleDateFormat("d", Locale.getDefault())

        while (iterCal.get(Calendar.MONTH) == displayMonth) {
            val d = iterCal.get(Calendar.DAY_OF_MONTH)
            val m = iterCal.get(Calendar.MONTH) + 1
            val y = iterCal.get(Calendar.YEAR)

            val lunar = KoreanLunarCalendarUtils.getLunarDateOf(y, m, d)
            val lunarLabel = "(${lunar.lunMonth}-${lunar.lunDay})"

            val isToday = d == todayDay && iterCal.get(Calendar.MONTH) == todayMonth && y == todayYear

            calendarDays.add(
                CalendarDay(
                    gregorian     = dateFormat.format(iterCal.time),
                    month         = m,
                    year          = y,
                    lunar         = lunarLabel,
                    isToday       = isToday,
                    isAnshiil     = isAnshiilDay(iterCal),
                    isImportantDay = isImportantDay(iterCal)
                )
            )
            iterCal.add(Calendar.DAY_OF_MONTH, 1)
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }

    // ✅ Чиста функція: нормалізуємо до опівночі, щоб мілісекунди не впливали
    private fun isAnshiilDay(calendar: Calendar): Boolean {
        val midnight = Calendar.getInstance().apply {
            timeInMillis = calendar.timeInMillis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val days = ((midnight - anshiilReference) / 86_400_000L).toInt()
        return days % 8 == 0
    }

    private fun isAnshiilDayTomorrow(calendar: Calendar): Boolean {
        val tomorrow = (calendar.clone() as Calendar).apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }
        return isAnshiilDay(tomorrow)
    }

    private fun isImportantDay(calendar: Calendar): Boolean {
        return importantDates.any {
            val c = it.importantCalendar()
            calendar.get(Calendar.YEAR)         == c.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH)        == c.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)
        }
    }

    private fun isImportantToday(date: String): Boolean {
        val today = Calendar.getInstance()
        val c = date.importantCalendar()
        return c.get(Calendar.YEAR)        == today.get(Calendar.YEAR) &&
                c.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
    }

    private fun checkImportantToday() {
        val todayIndex = importantDates.indexOfFirst { isImportantToday(it) }
        if (todayIndex >= 0) showImportantDay(todayIndex) else hideImportantDay()
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

    private fun scheduleNotification() {
        val prefs = getSharedPreferences("HeavenlyCalendarPrefs", MODE_PRIVATE)
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Calendar.getInstance().time)

        if (prefs.getString("last_notified_date", "") == todayDate) return

        val triggerCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if (isAnshiilDayTomorrow(triggerCal)) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                this, 0,
                Intent(this, NotificationReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerCal.timeInMillis, pendingIntent)
            prefs.edit().putString("last_notified_date", todayDate).apply()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Heavenly Calendar Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Notifies on the Day of Anshiil" }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

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
            1    -> getString(R.string.ordinal_one, n)
            2    -> getString(R.string.ordinal_two, n)
            3    -> getString(R.string.ordinal_few, n)
            else -> getString(R.string.ordinal_other, n)
        }
    }

    private fun splitAndGetGregorianDay(day: String): String {
        val parts = day.split(" ")
        return if (parts.size == 2) parts[1] else "0"
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