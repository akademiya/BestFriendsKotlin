package com.vadym.gvd.bestfriendskotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.util.*

fun doNothing() {}

fun kidoListPopupMenu(context: Context, view: View, rv: androidx.recyclerview.widget.RecyclerView, kidoSize: Int, positionHide: Int?) {
    val popupMenu = PopupMenu(context, view)
    popupMenu.inflate(R.menu.kido_list_popup_menu)

    if (positionHide != null) {
        for (i in positionHide until 50) {
            popupMenu.menu.getItem(i).isVisible = false
        }
    }

    for (i in 0 until kidoSize) {
        popupMenu.menu.getItem(i).title = String.format(context.getString(R.string.kido_counter), (i + 1))
    }

    popupMenu.setOnMenuItemClickListener {
        when(it.itemId) {
            R.id.kido1 -> rv.smoothScrollToPosition(0)
            R.id.kido2 -> rv.smoothScrollToPosition(1)
            R.id.kido3 -> rv.smoothScrollToPosition(2)
            R.id.kido4 -> rv.smoothScrollToPosition(3)
            R.id.kido5 -> rv.smoothScrollToPosition(4)
            R.id.kido6 -> rv.smoothScrollToPosition(5)
            R.id.kido7 -> rv.smoothScrollToPosition(6)
            R.id.kido8 -> rv.smoothScrollToPosition(7)
            R.id.kido9 -> rv.smoothScrollToPosition(8)
            R.id.kido10 -> rv.smoothScrollToPosition(9)

            R.id.kido11 -> rv.smoothScrollToPosition(10)
            R.id.kido12 -> rv.smoothScrollToPosition(11)
            R.id.kido13 -> rv.smoothScrollToPosition(12)
            R.id.kido14 -> rv.smoothScrollToPosition(13)
            R.id.kido15 -> rv.smoothScrollToPosition(14)
            R.id.kido16 -> rv.smoothScrollToPosition(15)
            R.id.kido17 -> rv.smoothScrollToPosition(16)
            R.id.kido18 -> rv.smoothScrollToPosition(17)
            R.id.kido19 -> rv.smoothScrollToPosition(18)
            R.id.kido20 -> rv.smoothScrollToPosition(19)

            R.id.kido21 -> rv.smoothScrollToPosition(20)
            R.id.kido22 -> rv.smoothScrollToPosition(21)
            R.id.kido23 -> rv.smoothScrollToPosition(22)
            R.id.kido24 -> rv.smoothScrollToPosition(23)
            R.id.kido25 -> rv.smoothScrollToPosition(24)
            R.id.kido26 -> rv.smoothScrollToPosition(25)
            R.id.kido27 -> rv.smoothScrollToPosition(26)
            R.id.kido28 -> rv.smoothScrollToPosition(27)
            R.id.kido29 -> rv.smoothScrollToPosition(28)
            R.id.kido30 -> rv.smoothScrollToPosition(29)

            R.id.kido31 -> rv.smoothScrollToPosition(30)
            R.id.kido32 -> rv.smoothScrollToPosition(31)
            R.id.kido33 -> rv.smoothScrollToPosition(32)
            R.id.kido34 -> rv.smoothScrollToPosition(33)
            R.id.kido35 -> rv.smoothScrollToPosition(34)
            R.id.kido36 -> rv.smoothScrollToPosition(35)
            R.id.kido37 -> rv.smoothScrollToPosition(36)
            R.id.kido38 -> rv.smoothScrollToPosition(37)
            R.id.kido39 -> rv.smoothScrollToPosition(38)
            R.id.kido40 -> rv.smoothScrollToPosition(39)

            R.id.kido41 -> rv.smoothScrollToPosition(40)
            R.id.kido42 -> rv.smoothScrollToPosition(41)
            R.id.kido43 -> rv.smoothScrollToPosition(42)
            R.id.kido44 -> rv.smoothScrollToPosition(43)
            R.id.kido45 -> rv.smoothScrollToPosition(44)
            R.id.kido46 -> rv.smoothScrollToPosition(45)
            R.id.kido47 -> rv.smoothScrollToPosition(46)
            R.id.kido48 -> rv.smoothScrollToPosition(47)
            R.id.kido49 -> rv.smoothScrollToPosition(48)
            R.id.kido50 -> rv.smoothScrollToPosition(49)
        }
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
    return SimpleDateFormat("dd MMMM yyyy", deviceLocale()).format(result)
}

fun Date.formatterDate() : String {
    return SimpleDateFormat("dd MMMM yyyy", deviceLocale()).format(this)
}

fun Intent.noAnimation() : Intent {
    return addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
}

fun screenOn(context: Context) {
    (context as Activity).window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun String.formatCustomPeriod() : String {
//    var y = 0
//    var m = 0
//    var d = 0
//    var intoD = this.toInt()
//    y = intoD / 365
//    m = (intoD / 30.4167).toInt()
//    d = (intoD - (30.4167 * m)).toInt()
//    return "$y.$m.$d"
//}