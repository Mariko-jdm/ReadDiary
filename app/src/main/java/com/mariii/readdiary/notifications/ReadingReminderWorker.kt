package com.mariii.readdiary.notifications

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mariii.readdiary.MainActivity
import com.mariii.readdiary.R

class ReadingReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        showNotification()

        return Result.success()
    }

    private fun showNotification() {

        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        )

        val pendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val notification =
            NotificationCompat.Builder(
                applicationContext,
                NotificationHelper.CHANNEL_ID
            )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("SpellRead")
                .setContentText("Не забудьте продолжить чтение")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        if (
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            NotificationManagerCompat
                .from(applicationContext)
                .notify(1, notification)
        }
    }
}