package com.task.praytimes.times.presentation.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.task.praytimes.times.presentation.view.MainActivity
import com.task.praytimes.R
import com.task.praytimes.times.Constants

class AlarmReceiver : BroadcastReceiver() {
    private val TAG = "TAG AlarmReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val timing = it.getStringExtra(Constants.EXTRA_TIMING_KEY) ?: return@let
            val time = it.getStringExtra(Constants.EXTRA_TIME_KEY)
            Log.i(TAG, "onAlarmReceive: $timing at $time")
            context?.let {context ->
                showNotification(context, timing)
            } ?: return@let
        }
    }

    private fun showNotification(
        appContext: Context,
        timing: String,
    ) {
        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivities(
                appContext,
                0,
                arrayOf(intent),
                PendingIntent.FLAG_IMMUTABLE
            )
        val notification =
            NotificationCompat.Builder(appContext, Constants.ALARM_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Praying Time")
                .setContentText("pray time")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .setBigContentTitle("It's time now for $timing praying at your local time")
                        .setSummaryText("$timing pray time")
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build()
        notificationManager.notify(Constants.ALARM_NOTIFICATION_ID, notification)
    }
}