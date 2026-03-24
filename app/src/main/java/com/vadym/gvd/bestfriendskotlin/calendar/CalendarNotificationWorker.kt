package com.vadym.gvd.bestfriendskotlin.calendar

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.vadym.gvd.bestfriendskotlin.FirebaseStorage
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.deviceLocale
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

const val NOTIFICATION_CHANNEL_ID = "HeavenlyCalendarChannel"

class CalendarNotificationWorker(
    private val ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        createNotificationChannel()

        val tomorrow = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Завантажуємо важливі дати з Firebase
        val importantDates = fetchImportantDates()

        val isTomorrowImportant = importantDates.any {
            val c = it.importantCalendar()
            // Порівнюємо тільки місяць і день (щорічні події)
            c.get(Calendar.MONTH) == tomorrow.get(Calendar.MONTH) &&
                    c.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)
        }

        val isTomorrowAnshiil = isAnshiilDay(tomorrow)

        when {
            isTomorrowImportant -> {
                val label = importantDates
                    .indexOfFirst {
                        val c = it.importantCalendar()
                        c.get(Calendar.MONTH) == tomorrow.get(Calendar.MONTH) &&
                                c.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)
                    }
                    .takeIf { it >= 0 }
                    ?.let { idx ->
                        ctx.resources.getIdentifier("day${idx + 1}_title", "string", ctx.packageName)
                            .takeIf { it != 0 }
                            ?.let { ctx.getString(it) }
                    } ?: ctx.getString(R.string.important_day)

                sendNotification(
                    title = label,
//                    text = ctx.getString(R.string.notification_important_text),
//                    text = ctx.getString(R.string.notification_important_text, label),
                    id = NOTIF_ID_IMPORTANT
                )
            }
            isTomorrowAnshiil -> sendNotification(
                title = ctx.getString(R.string.notification_anshiil_title),
//                text = ctx.getString(R.string.notification_anshiil_text),
                id = NOTIF_ID_ANSHIIL
            )
        }

        return Result.success()
    }

    // Suspend-функція для отримання дат з Firebase
    private suspend fun fetchImportantDates(): List<String> {
        return kotlinx.coroutines.suspendCancellableCoroutine { cont ->
            FirebaseStorage.instance.listHollyDaysFromFB { list ->
                val dates = list.mapNotNull { it.day?.let(::splitAndGetGregorianDay) }
                cont.resume(dates) {}
            }
        }
    }

    private fun isAnshiilDay(calendar: Calendar): Boolean {
        val reference = Calendar.getInstance().apply {
            set(2004, Calendar.MAY, 5, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val days = ((calendar.timeInMillis - reference) / 86_400_000L).toInt()
        return days % 8 == 0
    }

    private fun sendNotification(title: String, id: Int) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) return

        val intent = Intent(ctx, HeavenlyCalendarView::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            ctx, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.mense_dark)
            .setContentTitle(title)
//            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(ctx).notify(id, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Heavenly Calendar Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Notifies on important and Anshiil days" }
            (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun splitAndGetGregorianDay(day: String): String {
        val parts = day.split(" ")
        return if (parts.size == 2) parts[1] else "0"
    }

    companion object {
        const val NOTIF_ID_ANSHIIL = 1001
        const val NOTIF_ID_IMPORTANT = 1002
        private const val WORK_NAME = "HeavenlyCalendarDailyCheck"

        // Викликати один раз — наприклад з Application.onCreate()
        fun schedule(context: Context) {
            val now = Calendar.getInstance()
            // Розраховуємо затримку до наступної 9:00
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (timeInMillis <= now.timeInMillis) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            val delayMs = target.timeInMillis - now.timeInMillis

            val request = PeriodicWorkRequestBuilder<CalendarNotificationWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED) // Firebase потребує мережі
                        .build()
                )
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, // не перезапускати якщо вже є
                request
            )
        }
    }

    fun String.importantCalendar(): Calendar {
        val sdf = SimpleDateFormat("d/MM/yyyy", deviceLocale())
        val parsed = sdf.parse(this)
        return Calendar.getInstance().apply { time = parsed ?: Date() }
    }
}