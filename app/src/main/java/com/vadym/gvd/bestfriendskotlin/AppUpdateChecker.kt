package com.vadym.gvd.bestfriendskotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability

class AppUpdateChecker(private val activity: Activity) {

    private val prefs = activity.getSharedPreferences("update_prefs", Context.MODE_PRIVATE)
    private val appUpdateManager = AppUpdateManagerFactory.create(activity)

    // ---------------------------------------------------------------
    // Автоматична перевірка — викликати з MainActivity.onCreate()
    // Показує діалог не частіше ніж раз на CHECK_INTERVAL_DAYS днів
    // ---------------------------------------------------------------
    fun checkOnLaunchIfNeeded() {
        if (!shouldCheck()) return
        checkForUpdate(silent = true)
    }

    fun checkManually() {
        checkForUpdate(silent = false)
    }

    private fun checkForUpdate(silent: Boolean) {
        val infoTask = appUpdateManager.appUpdateInfo

        infoTask.addOnSuccessListener { info ->
            saveLastCheckTime()

            when (info.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    showUpdateDialog(
                        currentVersion  = getCurrentVersionName(),
                        isFlexible      = info.updatePriority() < HIGH_PRIORITY
                    )
                }
                UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                    if (!silent) showUpToDateDialog()
                }
                else -> {
                    if (!silent) showUpToDateDialog()
                }
            }
        }

        infoTask.addOnFailureListener {
            if (!silent) showFallbackDialog()
        }
    }

    private fun showUpdateDialog(currentVersion: String, isFlexible: Boolean) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(activity.getString(R.string.update_available_title))
            .setMessage(
                activity.getString(R.string.update_available_message, currentVersion)
            )
            .setPositiveButton(activity.getString(R.string.update_now)) { _, _ ->
                openPlayStore()
            }
            .setNegativeButton(activity.getString(R.string.update_later), null)
            .setCancelable(true)
            .show()
    }

    private fun showUpToDateDialog() {
        MaterialAlertDialogBuilder(activity)
            .setTitle(activity.getString(R.string.update_up_to_date_title))
            .setMessage(activity.getString(R.string.update_up_to_date_message))
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun showFallbackDialog() {
        // Коли Play API недоступний — просто пропонуємо відкрити магазин
        MaterialAlertDialogBuilder(activity)
            .setTitle(activity.getString(R.string.update_check_failed_title))
            .setMessage(activity.getString(R.string.update_check_failed_message))
            .setPositiveButton(activity.getString(R.string.open_store)) { _, _ ->
                openPlayStore()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun openPlayStore() {
        val pkg = activity.packageName
        runCatching {
            activity.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkg"))
            )
        }.onFailure {
            activity.startActivity(
                Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$pkg"))
            )
        }
    }

    private fun getCurrentVersionName(): String =
        runCatching {
            activity.packageManager
                .getPackageInfo(activity.packageName, 0)
                .versionName ?: "—"
        }.getOrDefault("—")

    private fun shouldCheck(): Boolean {
        val last = prefs.getLong(KEY_LAST_CHECK, 0L)
        val elapsed = System.currentTimeMillis() - last
        return elapsed > CHECK_INTERVAL_MS
    }

    private fun saveLastCheckTime() {
        prefs.edit().putLong(KEY_LAST_CHECK, System.currentTimeMillis()).apply()
    }

    companion object {
        private const val KEY_LAST_CHECK     = "last_update_check"
        private const val CHECK_INTERVAL_DAYS = 3L
        private const val CHECK_INTERVAL_MS   = CHECK_INTERVAL_DAYS * 24 * 60 * 60 * 1000L
        private const val HIGH_PRIORITY        = 4
    }
}