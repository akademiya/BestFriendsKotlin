package com.vadym.gvd.bestfriendskotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.vadym.gvd.bestfriendskotlin.calendar.CalendarNotificationWorker
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinDatabase
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinRepository
import com.vadym.gvd.bestfriendskotlin.easter_egg.scheduleDailyCoin
import kotlinx.coroutines.launch


@SuppressLint("Registered")
class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val languageCode = savedLanguage
        setLocale(languageCode)

//        val sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
//        val languageCode = sharedPreferences.getString("language", "en") ?: "en"
//        MainActivity().setLocale(this, languageCode)
//        setLocale(this, languageCode)
        DarkModePreferences(this).applyMode()
        CalendarNotificationWorker.schedule(this)

        kotlinx.coroutines.MainScope().launch {
            val prefs = getSharedPreferences("daily_coin", Context.MODE_PRIVATE)
            val repo  = CoinRepository(CoinDatabase.getInstance(this@AndroidApplication).coinDao())
            repo.seedCoins()           // посіяти монети якщо ще немає
            repo.pickDailyCoinIfNeeded(prefs)  // вибрати монету дня одразу
        }
        scheduleDailyCoin(this)
    }
}

