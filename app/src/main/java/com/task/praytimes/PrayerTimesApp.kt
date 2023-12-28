package com.task.praytimes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.task.praytimes.times.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PrayerTimesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val alertsChannel = NotificationChannel(
            Constants.ALARM_NOTIFICATION_CHANNEL_ID,
            "Prayer Times Alerts",
            NotificationManager.IMPORTANCE_HIGH
        )
        alertsChannel.description = "get alerts for prayer times"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(alertsChannel)
    }
}