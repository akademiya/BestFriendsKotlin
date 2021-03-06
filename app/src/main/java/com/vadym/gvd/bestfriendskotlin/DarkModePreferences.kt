package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.preference.PreferenceManager

class DarkModePreferences(context: Context) {
    companion object {
        private const val DARK_STATUS = "DARK_STATUS"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, 0)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
}