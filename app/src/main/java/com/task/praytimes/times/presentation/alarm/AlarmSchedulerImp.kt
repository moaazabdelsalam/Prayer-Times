package com.task.praytimes.times.presentation.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.task.praytimes.times.Constants
import java.time.ZoneId

class AlarmSchedulerImp(private val context: Context) : AlarmScheduler {
    private val TAG = "TAG AlarmSchedulerImp"
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmItem) {
        Log.i(
            TAG,
            "schedule at: ${item.time.year} +" +
                    " ${item.time.month.value} +" +
                    " ${item.time.dayOfMonth} +" +
                    " ${item.time.dayOfYear} +" +
                    " ${item.time.hour} +" +
                    " ${item.time.minute}"
        )
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constants.EXTRA_TIMING_KEY, item.timing)
            putExtra(
                Constants.EXTRA_TIME_KEY,
                "${item.time.year} +" +
                        " ${item.time.month.value} +" +
                        " ${item.time.dayOfMonth} +" +
                        " ${item.time.hour} +" +
                        " ${item.time.minute}"
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager?.canScheduleExactAlarms() == false) {
                Intent().also { mIntent ->
                    mIntent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    context.startActivity(mIntent)
                }
            }
        }
        item.time.atZone(ZoneId.systemDefault()).let { triggerZonedDateTime ->
            if (triggerZonedDateTime.toInstant().toEpochMilli() > System.currentTimeMillis()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                    PendingIntent.getBroadcast(
                        context,
                        item.time.year + item.time.month.value + item.time.dayOfMonth + item.time.dayOfYear
                                + item.time.hour + item.time.minute,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                )
            } else {
                Log.i(TAG, "Scheduled time is in the past. Alarm not scheduled.")
            }
        }
    }

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.time.year + item.time.month.value + item.time.dayOfMonth + item.time.dayOfYear
                        + item.time.hour + item.time.minute,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}