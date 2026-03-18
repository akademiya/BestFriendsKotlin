package com.vadym.gvd.bestfriendskotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException

class GetVersionCode(private val activity: AppCompatActivity) {

    private val APP_NAME = "me.vadym.adv.tfprayer"
    private val DAYS_UNTIL_PROMPT = 1F

    fun check() {
        activity.lifecycleScope.launch {
            val onlineVersion = fetchVersionFromPlay()
            onlineVersion?.let { handleVersion(it) }
        }
    }

    // Мережевий запит — виконується у фоновому потоці (Dispatchers.IO)
    private suspend fun fetchVersionFromPlay(): String? = withContext(Dispatchers.IO) {
        try {
            val document = Jsoup
                .connect("https://play.google.com/store/apps/details?id=${activity.packageName}&hl=en")
                .timeout(30000)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get()

            val element = document.getElementsContainingOwnText("Current Version")
            var newVersion: String? = null
            for (ele in element) {
                ele.siblingElements().forEach { sib ->
                    newVersion = sib.text()
                }
            }
            Log.i("VerrAppTF", "onlineVersion = $newVersion")
            newVersion
        } catch (e: IOException) {
            Log.e("VerrAppTF", "fetchVersionFromPlay error: ${e.message}")
            null
        }
    }

    // Порівняння версій — виконується на головному потоці (Main)
    private fun handleVersion(onlineVersion: String) {
        try {
            val currentVersion = activity.packageManager
                .getPackageInfo(activity.packageName, 0)
                .versionName

            val online = onlineVersion.toFloatOrNull() ?: return
            val current = currentVersion?.toFloatOrNull() ?: return

            Log.i("VerrAppTF", "current=$current, online=$online")

            if (current < online) {
                appLaunched(activity)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("VerrAppTF", "Package not found: ${e.message}")
        }
    }

    private fun appLaunched(context: Context) {
        val prefs = context.getSharedPreferences("appupdate", 0)
        val editor = prefs.edit()

        var dateFirstLaunch = prefs.getLong("date_firstlaunch", 0)
        if (dateFirstLaunch == 0L) {
            dateFirstLaunch = System.currentTimeMillis()
            editor.putLong("date_firstlaunch", dateFirstLaunch)
            showUpdateDialog(context, editor)
        } else {
            if (System.currentTimeMillis() >= dateFirstLaunch + DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000) {
                showUpdateDialog(context, editor)
            } else {
                editor.apply()
            }
        }
    }

    private fun showUpdateDialog(context: Context, editor: SharedPreferences.Editor?) {
        val dialog = Dialog(context, 0)

        val ll = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }

        val tv = TextView(context).apply {
            text = context.resources.getText(R.string.up_app_title)
            textSize = 16F
            width = 640
            setPadding(50, 30, 50, 30)
        }
        ll.addView(tv)

        val btnYes = Button(context).apply {
            text = context.resources.getText(R.string.update)
            setTextColor(context.resources.getColor(R.color.white))
            setBackgroundColor(context.resources.getColor(R.color.primary))
            setPadding(0, 40, 0, 40)
            setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$APP_NAME")))
                dialog.dismiss()
            }
        }
        ll.addView(btnYes)

        val btnLater = Button(context).apply {
            text = context.resources.getText(R.string.later_update)
            setTextColor(context.resources.getColor(R.color.white))
            setBackgroundColor(context.resources.getColor(R.color.color_text))
            setPadding(0, 40, 0, 40)
            setOnClickListener {
                editor?.putLong("date_firstlaunch", System.currentTimeMillis())
                editor?.commit()
                dialog.dismiss()
            }
        }
        ll.addView(btnLater)

        dialog.setContentView(ll)
        dialog.show()
    }
}