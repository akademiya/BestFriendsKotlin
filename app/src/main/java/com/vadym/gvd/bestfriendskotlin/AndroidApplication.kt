package com.vadym.gvd.bestfriendskotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.vadym.gvd.bestfriendskotlin.calendar.CalendarNotificationWorker
import com.vadym.gvd.bestfriendskotlin.easter_egg.scheduleDailyCoin


@SuppressLint("Registered")
class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language", "en") ?: "en"
        MainActivity().setLocale(this, languageCode)
        DarkModePreferences(this).applyMode()
        CalendarNotificationWorker.schedule(this)
        scheduleDailyCoin(this)
    }
}

