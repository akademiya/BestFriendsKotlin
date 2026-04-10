package com.vadym.gvd.bestfriendskotlin.easter_egg

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.vadym.gvd.bestfriendskotlin.R
import java.util.Calendar
import java.util.concurrent.TimeUnit

class DailyCoinWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val prefs = applicationContext.getSharedPreferences("daily_coin", Context.MODE_PRIVATE)
        val repo  = CoinRepository(CoinDatabase.getInstance(applicationContext).coinDao())
        repo.pickDailyCoinIfNeeded(prefs)
        showCoinNotification(applicationContext)
        return Result.success()
    }
}

fun scheduleDailyCoin(context: Context) {
    val request = PeriodicWorkRequestBuilder<DailyCoinWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(calculateDelayUntilNoon(), TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "daily_coin",
        ExistingPeriodicWorkPolicy.KEEP,
        request
    )
}

// ─── Розраховує затримку до 12:00 сьогодні або завтра ────────────────────────
fun calculateDelayUntilNoon(): Long {
    val now  = Calendar.getInstance()
    val noon = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    // Якщо 10:00 вже минула — плануємо на завтра
    if (now.after(noon)) noon.add(Calendar.DAY_OF_MONTH, 1)
    return noon.timeInMillis - now.timeInMillis
}

// ─── Нотифікація "Сьогодні є монета" ─────────────────────────────────────────
fun showCoinNotification(context: Context) {
    val channelId = "daily_coin_channel"
    val manager   = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Канал потрібен для Android 8+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            context.getString(R.string.notif_daily_coin),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply { description = context.getString(R.string.notif_hide_coin) }
        manager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_shimjeong_coin)
        .setContentTitle(context.getString(R.string.notif_exist_coin))
        .setContentText(context.getString(R.string.notif_find_coin))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .build()

    manager.notify(1001, notification)
}