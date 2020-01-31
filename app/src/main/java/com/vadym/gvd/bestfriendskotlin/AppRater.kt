package com.vadym.gvd.bestfriendskotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView


open class AppRater {
    private val APP_NAME = "me.vadym.adv.tfprayer"// Package Name
    private val DAYS_UNTIL_PROMPT = 1 //Min number of days
    private val LAUNCHES_UNTIL_PROMPT = 3 //Min number of launches

    fun appLaunched(context: Context) {
        val prefs = context.getSharedPreferences("apprater", 0)
        if (prefs.getBoolean("dontShowAgain", false)) {
            return
        }

        val editor = prefs.edit()

        // Increment launch counter
        val launchCount = prefs.getLong("launch_count", 0) + 1
        editor.putLong("launch_count", launchCount)

        // Get date of first launch
        var dateFirstLaunch: Long? = prefs.getLong("date_firstlaunch", 0)
        if (dateFirstLaunch == 0L) {
            dateFirstLaunch = System.currentTimeMillis()
            editor.putLong("date_firstlaunch", dateFirstLaunch)
        }

        // Wait at least n days before opening
        if (launchCount >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= dateFirstLaunch!! + DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000) {
                showRateDialog(context, editor)
            }
        }

        editor.commit()
    }

    private fun showRateDialog(context: Context, editor: SharedPreferences.Editor?) {
        val dialog = Dialog(context, 0)
        dialog.setTitle(context.resources.getText(R.string.rate))

        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.VERTICAL

        val tv = TextView(context)
        tv.text = context.resources.getText(R.string.to_rate)
        tv.textSize = 16F
        tv.width = 640
        tv.setPadding(50, 30, 50, 30)
        ll.addView(tv)

        val btnYes = Button(context)
        btnYes.text = context.resources.getText(R.string.rate)
        btnYes.setTextColor(context.resources.getColor(R.color.white))
        btnYes.setBackgroundColor(context.resources.getColor(R.color.primary))
        btnYes.setPadding(0, 30, 0, 30)
        btnYes.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$APP_NAME")))
            dialog.dismiss()
        }
        ll.addView(btnYes)

        val btnLater = Button(context)
        btnLater.text = context.resources.getText(R.string.later_rate)
        btnLater.setTextColor(context.resources.getColor(R.color.white))
        btnLater.setBackgroundColor(context.resources.getColor(R.color.color_text))
        btnLater.setPadding(0, 30, 0, 30)
        btnLater.setOnClickListener {
            dialog.dismiss()
        }
        ll.addView(btnLater)

        val btnNo = Button(context)
        btnNo.text = context.resources.getText(R.string.no_rate)
        btnNo.setTextColor(context.resources.getColor(R.color.white))
        btnNo.setBackgroundColor(context.resources.getColor(R.color.abc_tint_switch_track))
        btnNo.setPadding(0, 30, 0, 30)
        btnNo.setOnClickListener {
            if (editor != null) {
                editor.putBoolean("dontShowAgain", true)
                editor.commit()
            }
            dialog.dismiss()
        }
        ll.addView(btnNo)

        dialog.setContentView(ll)
        dialog.show()
    }
}