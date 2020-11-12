package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object CheckTheme {
    fun checkTheme(context: Context, delegate: AppCompatDelegate) {
        when (DarkModePreferences(context).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
        }
    }
}