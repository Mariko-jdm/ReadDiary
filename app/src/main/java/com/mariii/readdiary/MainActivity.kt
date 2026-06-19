package com.mariii.readdiary

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.mariii.readdiary.navigation.AppNavigation
import com.mariii.readdiary.notifications.NotificationHelper
import com.mariii.readdiary.notifications.ReminderScheduler
import com.mariii.readdiary.ui.theme.ReadDiaryTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                100
            )
        }

        NotificationHelper.createChannel(this)
        ReminderScheduler.scheduleReminder(this)


        setContent {

            ReadDiaryTheme {

                AppNavigation()
            }
        }
    }
}