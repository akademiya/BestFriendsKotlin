package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import java.util.Locale

val Context.savedLanguage: String
    get() = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        .getString("language", "en") ?: "en"

fun Context.withLocale(languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = resources.configuration.also { it.setLocale(locale) }
    @Suppress("DEPRECATION")
    resources.updateConfiguration(config, resources.displayMetrics)
    return createConfigurationContext(config)
}

fun Context.setLocale(languageCode: String) {
    withLocale(languageCode)
    getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        .edit().putString("language", languageCode).apply()
}