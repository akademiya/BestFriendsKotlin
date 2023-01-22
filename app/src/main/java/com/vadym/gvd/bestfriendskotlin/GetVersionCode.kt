package com.vadym.gvd.bestfriendskotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException


class GetVersionCode(val view: AppCompatActivity) : AsyncTask<Void, String, String>() {
    private val APP_NAME = "me.vadym.adv.tfprayer"
    private val DAYS_UNTIL_PROMPT = 1F //Min number of days

    override fun doInBackground(vararg params: Void?): String {
        var newVersion: String? = null
        var currVer = ""

        try {
            val document: Document? = Jsoup.connect("https://play.google.com/store/apps/details?id=" + view.packageName + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
            if (document != null) {
                val element: Elements = document.getElementsContainingOwnText("Current Version")
                currVer = element.text()
                for (ele in element) {
                    if (ele.siblingElements() != null) {
                        val sibElemets: Elements = ele.siblingElements()
                        for (sibElemet in sibElemets) {
                            newVersion = sibElemet.text()
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.i("VerrAppTF", "newVersion = $newVersion, currVer = $currVer")
        return newVersion ?: currVer
    }

    override fun onPostExecute(onlineVersion: String?) {
        super.onPostExecute(onlineVersion)
        val currentVersion: String
        try {
            currentVersion = view.packageManager.getPackageInfo(view.packageName, 0).versionName
            if (onlineVersion != null && onlineVersion.isNotEmpty()) {
                if (currentVersion.toFloat() < onlineVersion.toFloat()) {
                    appLaunched(view)
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun appLaunched(context: Context) {
        val prefs = context.getSharedPreferences("appupdate", 0)
        val editor = prefs.edit()

        // Get date of first launch
        var dateFirstLaunch: Long = prefs.getLong("date_firstlaunch", 0)
        if (dateFirstLaunch == 0L) {
            dateFirstLaunch = System.currentTimeMillis()
            editor.putLong("date_firstlaunch", dateFirstLaunch)
            showUpdateDialog(context, editor)
        } else {
            // Wait at least n days before opening
            if (System.currentTimeMillis() >= dateFirstLaunch + DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000) {
                showUpdateDialog(context, editor)
            } else {
                editor.apply()
            }
        }
    }

    private fun showUpdateDialog(context: Context, editor: SharedPreferences.Editor?) {
        val dialog = Dialog(context, 0)

        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.VERTICAL

        val tv = TextView(context)
        tv.text = context.resources.getText(R.string.up_app_title)
        tv.textSize = 16F
        tv.width = 640
        tv.setPadding(50, 30, 50, 30)
        ll.addView(tv)

        val btnYes = Button(context)
        btnYes.text = context.resources.getText(R.string.update)
        btnYes.setTextColor(context.resources.getColor(R.color.white))
        btnYes.setBackgroundColor(context.resources.getColor(R.color.primary))
        btnYes.setPadding(0, 40, 0, 40)
        btnYes.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$APP_NAME")))
            dialog.dismiss()
        }
        ll.addView(btnYes)

        val btnLater = Button(context)
        btnLater.text = context.resources.getText(R.string.later_update)
        btnLater.setTextColor(context.resources.getColor(R.color.white))
        btnLater.setBackgroundColor(context.resources.getColor(R.color.color_text))
        btnLater.setPadding(0, 40, 0, 40)
        btnLater.setOnClickListener {
            if (editor != null) {
                editor.putLong("date_firstlaunch", System.currentTimeMillis())
                editor.commit()
            }
            dialog.dismiss()
        }
        ll.addView(btnLater)

        dialog.setContentView(ll)
        dialog.show()
    }
}