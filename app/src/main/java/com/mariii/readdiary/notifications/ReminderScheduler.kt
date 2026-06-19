package com.mariii.readdiary.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import java.util.Calendar

object ReminderScheduler {

    fun scheduleReminder(
        context: Context
    ) {

        val prefs = context.getSharedPreferences(
            "statistics_prefs",
            Context.MODE_PRIVATE
        )

        val frequency =
            prefs.getString(
                "reminder_frequency",
                "ежедневно"
            ) ?: "ежедневно"

        val reminderTime =
            prefs.getString(
                "reminder_time",
                "12:00"
            ) ?: "12:00"

        val intervalDays = when (frequency) {

            "каждые 2 дня" -> 2L

            "каждые 3 дня" -> 3L

            "каждые 5 дней" -> 5L

            "каждые 7 дней" -> 7L

            else -> 1L
        }

        val initialDelay =
            calculateInitialDelay(reminderTime)

        val request =
            PeriodicWorkRequestBuilder<ReadingReminderWorker>(
                intervalDays,
                TimeUnit.DAYS
            )
                .setInitialDelay(
                    initialDelay,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "daily_reading_reminder",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }

    private fun calculateInitialDelay(
        reminderTime: String
    ): Long {

        val parts = reminderTime.split(":")

        val hour = parts[0].toInt()
        val minute = parts[1].toInt()

        val now = Calendar.getInstance()

        val target = Calendar.getInstance().apply {

            set(
                Calendar.HOUR_OF_DAY,
                hour
            )

            set(
                Calendar.MINUTE,
                minute
            )

            set(
                Calendar.SECOND,
                0
            )

            set(
                Calendar.MILLISECOND,
                0
            )
        }

        if (target.before(now)) {

            target.add(
                Calendar.DAY_OF_MONTH,
                1
            )
        }

        return target.timeInMillis - now.timeInMillis
    }
}