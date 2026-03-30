package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate

class DarkModePreferences(context: Context) {

    companion object {
        private const val KEY_DARK_MODE = "dark_mode_status"

        const val MODE_FOLLOW_SYSTEM = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM // -1
        const val MODE_LIGHT         = AppCompatDelegate.MODE_NIGHT_NO             //  1
        const val MODE_DARK          = AppCompatDelegate.MODE_NIGHT_YES            //  2
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var mode: Int
        get() = prefs.getInt(KEY_DARK_MODE, MODE_FOLLOW_SYSTEM)
        set(value) {
            prefs.edit().putInt(KEY_DARK_MODE, value).apply()
        }

    /** Застосовує збережений режим. Викликати в Application.onCreate() */
    fun applyMode() {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun enableDark()         = setAndApply(MODE_DARK)
    fun enableLight()        = setAndApply(MODE_LIGHT)
    fun followSystem()       = setAndApply(MODE_FOLLOW_SYSTEM)
    fun isDark(): Boolean    = mode == MODE_DARK

    private fun setAndApply(newMode: Int) {
        mode = newMode
        AppCompatDelegate.setDefaultNightMode(newMode)
    }
}