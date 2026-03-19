package com.vadym.gvd.bestfriendskotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun doNothing() {}

fun kidoListPopupMenu(
    context: Context,
    view: View,
    lm: LinearLayoutManager,
    kidoSize: Int
) {
    val popupMenu = PopupMenu(context, view)
    popupMenu.inflate(R.menu.kido_popup_menu)

    val menuSize = popupMenu.menu.size()

    for (i in 0 until menuSize) {
        val visible = i < kidoSize
        popupMenu.menu.getItem(i).apply {
            isVisible = visible
            if (visible) title = String.format(context.getString(R.string.kido_counter), i + 1)
        }
    }

    popupMenu.setOnMenuItemClickListener { item ->
        val position = when (item.itemId) {
            R.id.kido1  -> 0;  R.id.kido2  -> 1;  R.id.kido3  -> 2;  R.id.kido4  -> 3
            R.id.kido5  -> 4;  R.id.kido6  -> 5;  R.id.kido7  -> 6;  R.id.kido8  -> 7
            R.id.kido9  -> 8;  R.id.kido10 -> 9;  R.id.kido11 -> 10; R.id.kido12 -> 11
            R.id.kido13 -> 12; R.id.kido14 -> 13; R.id.kido15 -> 14; R.id.kido16 -> 15
            R.id.kido17 -> 16; R.id.kido18 -> 17; R.id.kido19 -> 18; R.id.kido20 -> 19
            R.id.kido21 -> 20; R.id.kido22 -> 21; R.id.kido23 -> 22; R.id.kido24 -> 23
            R.id.kido25 -> 24; R.id.kido26 -> 25; R.id.kido27 -> 26; R.id.kido28 -> 27
            R.id.kido29 -> 28; R.id.kido30 -> 29; R.id.kido31 -> 30; R.id.kido32 -> 31
            R.id.kido33 -> 32; R.id.kido34 -> 33; R.id.kido35 -> 34; R.id.kido36 -> 35
            R.id.kido37 -> 36; R.id.kido38 -> 37; R.id.kido39 -> 38; R.id.kido40 -> 39
            R.id.kido41 -> 40; R.id.kido42 -> 41; R.id.kido43 -> 42; R.id.kido44 -> 43
            R.id.kido45 -> 44; R.id.kido46 -> 45; R.id.kido47 -> 46; R.id.kido48 -> 47
            R.id.kido49 -> 48; R.id.kido50 -> 49
            else -> -1
        }
        if (position >= 0) lm.scrollToPositionWithOffset(position, 0)
        true
    }
    popupMenu.show()
}

fun deviceLocale() : Locale {
    val deviceLocaleLanguage = Locale.getDefault().language
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Locale.Builder().setLanguageTag(deviceLocaleLanguage).build()
    } else {
        Locale.getDefault()
    }
}

fun restartActivity(context: Context) {
    (context as Activity).finish()
    val intent = context.intent
    intent.noAnimation()
    context.startActivity(intent)
}

fun String.formatterDate() : String {
    val result = SimpleDateFormat("yyyyMMdd", deviceLocale()).parse(this)
    return SimpleDateFormat("dd.MM.yyyy", deviceLocale()).format(result!!)
}

fun Date.formatterDate() : String {
    return SimpleDateFormat("dd.MM.yyyy", deviceLocale()).format(this)
}

fun Intent.noAnimation() : Intent {
    return addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
}

fun screenOn(context: Context) {
    (context as Activity).window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

fun String.toHtml() : Spanned {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
}

//val importantSDF = SimpleDateFormat("dd/MM/yyyy", deviceLocale())
//
//fun String.importantCalendar(): Calendar {
//    val parsed = importantSDF.parse(this)
//    return Calendar.getInstance().apply { time = parsed!! }
//}