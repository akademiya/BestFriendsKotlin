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
    private val APP_PNAME = "me.vadym.adv.tfprayer"// Package Name
    private val DAYS_UNTIL_PROMPT = 1 //Min number of days
    private val LAUNCHES_UNTIL_PROMPT = 3 //Min number of launches

    fun app_launched(mContext: Context) {
        val prefs = mContext.getSharedPreferences("apprater", 0)
        if (prefs.getBoolean("dontshowagain", false)) {
            return
        }

        val editor = prefs.edit()

        // Increment launch counter
        val launch_count = prefs.getLong("launch_count", 0) + 1
        editor.putLong("launch_count", launch_count)

        // Get date of first launch
        var date_firstLaunch: Long? = prefs.getLong("date_firstlaunch", 0)
        if (date_firstLaunch == 0L) {
            date_firstLaunch = System.currentTimeMillis()
            editor.putLong("date_firstlaunch", date_firstLaunch)
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch!! + DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000) {
                showRateDialog(mContext, editor)
            }
        }

        editor.commit()
    }

    private fun showRateDialog(mContext: Context, editor: SharedPreferences.Editor?) {
        val dialog = Dialog(mContext, 0)
        dialog.setTitle(mContext.resources.getText(R.string.rate))

        val ll = LinearLayout(mContext)
        ll.orientation = LinearLayout.VERTICAL

        val tv = TextView(mContext)
        tv.text = mContext.resources.getText(R.string.to_rate)
        tv.textSize = 16F
        tv.width = 640
        tv.setPadding(50, 30, 50, 30)
        ll.addView(tv)

        val b1 = Button(mContext)
        b1.text = mContext.resources.getText(R.string.rate)
        b1.setTextColor(mContext.resources.getColor(R.color.white))
        b1.setBackgroundColor(mContext.resources.getColor(R.color.primary))
        b1.setPadding(0, 30, 0, 30)
        b1.setOnClickListener {
            mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$APP_PNAME")))
            dialog.dismiss()
        }
        ll.addView(b1)

        val b2 = Button(mContext)
        b2.text = mContext.resources.getText(R.string.later_rate)
        b2.setTextColor(mContext.resources.getColor(R.color.white))
        b2.setBackgroundColor(mContext.resources.getColor(R.color.color_text))
        b2.setPadding(0, 30, 0, 30)
        b2.setOnClickListener {
            dialog.dismiss()
        }
        ll.addView(b2)

        val b3 = Button(mContext)
        b3.text = mContext.resources.getText(R.string.no_rate)
        b3.setTextColor(mContext.resources.getColor(R.color.white))
        b3.setBackgroundColor(mContext.resources.getColor(R.color.abc_tint_switch_track))
        b3.setPadding(0, 30, 0, 30)
        b3.setOnClickListener {
            if (editor != null) {
                editor.putBoolean("dontshowagain", true)
                editor.commit()
            }
            dialog.dismiss()
        }
        ll.addView(b3)

        dialog.setContentView(ll)
        dialog.show()
    }
}