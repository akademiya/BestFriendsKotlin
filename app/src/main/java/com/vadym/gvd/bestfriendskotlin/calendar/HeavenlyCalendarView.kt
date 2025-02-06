package com.vadym.gvd.bestfriendskotlin.calendar

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.ChineseCalendar
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.fj.koreanlunarcalendar.KoreanLunarCalendarUtils
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


const val NOTIFICATION_CHANNEL_ID = "HeavenlyCalendarChannel"

class HeavenlyCalendarView: MainActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var titleMonthYear: TextView
    private val calendarDays = mutableListOf<CalendarDay>()
    private val currentCalendar: Calendar = Calendar.getInstance()
    private var heavenlyMonth = "0"
    private var heavenlyDay = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_calendar)

        titleMonthYear = findViewById(R.id.title_month_year)
        recyclerView = findViewById(R.id.recyclerViewCalendar)
        val prevMonth = findViewById<ImageView>(R.id.prev_month)
        val nextMonth = findViewById<ImageView>(R.id.next_month)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val viewHeavenlyDateToday = findViewById<TextView>(R.id.view_heavenly_date_today)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }

        recyclerView.layoutManager = GridLayoutManager(this, 7)
        recyclerView.adapter = CalendarAdapter(calendarDays)

        generateCalendar()
        createNotificationChannel()
        scheduleNotification()

        prevMonth.setOnClickListener {
            currentCalendar.add(Calendar.MONTH, -2)
            generateCalendar()
        }

        nextMonth.setOnClickListener {
            currentCalendar.add(Calendar.MONTH, 0)
            generateCalendar()
        }

        viewHeavenlyDateToday.text = calculateHeavenlyYear(heavenlyMonth ,heavenlyDay)
    }

    private fun generateCalendar() {
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val month = currentCalendar.get(Calendar.MONTH)
        val year = currentCalendar.get(Calendar.YEAR)

        val todayCalendar = Calendar.getInstance()
        val todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH)
        val todayMonth = todayCalendar.get(Calendar.MONTH)
        val todayYear = todayCalendar.get(Calendar.YEAR)

        val lunarCalendar = ChineseCalendar.getInstance()
        lunarCalendar.set(ChineseCalendar.YEAR, year)
        lunarCalendar.set(ChineseCalendar.MONTH, month)
        lunarCalendar.set(ChineseCalendar.DAY_OF_MONTH, 1)

        val lunarDate = KoreanLunarCalendarUtils.getLunarDateOf(todayYear, todayMonth + 1, todayDay)

        heavenlyMonth = lunarDate.lunMonth.toString()
        heavenlyDay = lunarDate.lunDay.toString()

        val dateFormat = SimpleDateFormat("d", Locale.getDefault())

        calendarDays.clear()

        titleMonthYear.text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(currentCalendar.time)

        val firstDayOfWeek = currentCalendar.get(Calendar.DAY_OF_WEEK)
        for (i in 1 until firstDayOfWeek) {
            calendarDays.add(CalendarDay("", "", false, false))
        }

        while (currentCalendar.get(Calendar.MONTH) == month) {
            val gregorianDay = dateFormat.format(currentCalendar.time)

            val lunarDate = KoreanLunarCalendarUtils.getLunarDateOf(
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH) + 1,
                currentCalendar.get(Calendar.DAY_OF_MONTH)
            )

            val lunarDay = "(${lunarDate.lunMonth}-${lunarDate.lunDay})"

            val isToday = (currentCalendar.get(Calendar.DAY_OF_MONTH) == todayDay &&
                    currentCalendar.get(Calendar.MONTH) == todayMonth &&
                    currentCalendar.get(Calendar.YEAR) == todayYear)


            calendarDays.add(CalendarDay(gregorianDay, lunarDay, isToday, isAnshiilDay(currentCalendar)))

            currentCalendar.add(Calendar.DAY_OF_MONTH, 1)
            lunarCalendar.add(ChineseCalendar.DAY_OF_MONTH, 1)
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }

    data class CalendarDay(val gregorian: String, val lunar: String, val isToday: Boolean, val isAnshiil: Boolean)

    private fun isAnshiilDay(calendar: Calendar): Boolean {
        val referenceCalendar = Calendar.getInstance().apply {
            set(2004, Calendar.MAY, 5) // May 5, 1960
        }

        val diffInMillis = calendar.timeInMillis - referenceCalendar.timeInMillis
        val daysDifference = (diffInMillis / (1000 * 60 * 60 * 24)).toInt()

        return daysDifference % 8 == 0
    }

    private fun isAnshiilDayTomorrow(calendar: Calendar): Boolean {
        val referenceCalendar = Calendar.getInstance().apply {
            set(2004, Calendar.MAY, 5) // Reference Date
        }

        val tomorrowCalendar = calendar.clone() as Calendar
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1) // Check for tomorrow

        val diffInMillis = tomorrowCalendar.timeInMillis - referenceCalendar.timeInMillis
        val daysDifference = (diffInMillis / (1000 * 60 * 60 * 24)).toInt()

        return daysDifference % 8 == 0
    }

    private fun scheduleNotification() {
        val sharedPreferences = getSharedPreferences("HeavenlyCalendarPrefs", MODE_PRIVATE)
        val lastNotifiedDate = sharedPreferences.getString("last_notified_date", "")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9) // Set the notification time (e.g., 9 AM)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

        if (lastNotifiedDate == todayDate) {
            return
        }

        if (isAnshiilDayTomorrow(calendar)) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            sharedPreferences.edit().putString("last_notified_date", todayDate).apply()
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Heavenly Calendar Notifications"
            val descriptionText = "Notifies on the Day of Anshiil"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun calculateHeavenlyYear(month: String, day: String) : String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, 0)
        val year = calendar.get(Calendar.YEAR) - 2012

        return getString(R.string.heavenly_date, getOrdinal(year), getOrdinal(month.toInt()), getOrdinal(day.toInt()))
    }

    private fun getOrdinal(number: Int): String {
        return when (number % 100) {
            11, 12, 13 -> getString(R.string.ordinal_other, number) // 11th, 12th, 13th
            else -> when (number % 10) {
                1 -> getString(R.string.ordinal_one, number)  // 1st
                2 -> getString(R.string.ordinal_two, number)  // 2nd
                3 -> getString(R.string.ordinal_few, number)  // 3rd
                else -> getString(R.string.ordinal_other, number)  // 4th, 5th, etc.
            }
        }
    }


    private fun handleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            drawer.openDrawer(GravityCompat.START)
        }
    }

}