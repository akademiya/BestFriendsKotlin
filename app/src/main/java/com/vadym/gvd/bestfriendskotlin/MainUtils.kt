package com.vadym.gvd.bestfriendskotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
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
    val themedContext = ContextThemeWrapper(context, R.style.KidoPopupMenu)
    val popupMenu = PopupMenu(themedContext, view)
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
        val position = popupMenu.menu.indexOfItem(item)
        if (position >= 0) lm.scrollToPositionWithOffset(position, 0)
        true
    }
    popupMenu.show()
}

private fun Menu.indexOfItem(target: MenuItem): Int {
    for (i in 0 until size()) if (getItem(i).itemId == target.itemId) return i
    return -1
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