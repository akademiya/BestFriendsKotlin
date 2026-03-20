package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager

class AppRater(private val context: Context,
               private val coinManager: CoinManager,
               private val onCoinsAwarded: ((Int) -> Unit)? = null) {

    companion object {
        private const val APP_PACKAGE = "me.vadym.adv.tfprayer"
        private const val DAYS_UNTIL_PROMPT = 1
        private const val LAUNCHES_UNTIL_PROMPT = 3
        private const val PREFS_NAME = "apprater"
        private const val KEY_DONT_SHOW = "dontShowAgain"
        private const val KEY_LAUNCH_COUNT = "launch_count"
        private const val KEY_FIRST_LAUNCH = "date_firstlaunch"
    }

    fun appLaunched() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.getBoolean(KEY_DONT_SHOW, false)) return

        val editor = prefs.edit()
        val launchCount = prefs.getLong(KEY_LAUNCH_COUNT, 0) + 1
        editor.putLong(KEY_LAUNCH_COUNT, launchCount)

        val dateFirstLaunch = prefs.getLong(KEY_FIRST_LAUNCH, 0).takeIf { it != 0L }
            ?: System.currentTimeMillis().also { editor.putLong(KEY_FIRST_LAUNCH, it) }

        editor.apply()

        if (launchCount >= LAUNCHES_UNTIL_PROMPT &&
            System.currentTimeMillis() >= dateFirstLaunch + DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000L
        ) {
            showRateDialog(editor)
        }
    }

    private fun showRateDialog(editor: SharedPreferences.Editor) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.rate)
            .setMessage(R.string.to_rate)
            .setCancelable(false)
            .setPositiveButton(R.string.rate) { dialog, _ ->
                openPlayStore()
                if (coinManager.rewardForRating()) {
                    onCoinsAwarded?.invoke(CoinManager.COINS_FOR_RATING)
                }
                dialog.dismiss()
            }
            .setNeutralButton(R.string.later_rate) { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(R.string.no_rate) { dialog, _ ->
                editor.putBoolean(KEY_DONT_SHOW, true).also { editor.apply() }
                dialog.dismiss()
            }
            .show()
    }

    private fun openPlayStore() {
        val uri = Uri.parse("market://details?id=$APP_PACKAGE")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        // Fallback на браузер, якщо Play Store не встановлений
        runCatching { context.startActivity(intent) }.onFailure {
            val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$APP_PACKAGE")
            context.startActivity(Intent(Intent.ACTION_VIEW, webUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}