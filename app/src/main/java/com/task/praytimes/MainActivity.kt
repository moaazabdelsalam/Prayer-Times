package com.task.praytimes

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.task.praytimes.times.Constants
import com.task.praytimes.times.data.local.LocalSourceImp
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.RemoteSourceImp
import com.task.praytimes.times.data.repo.RepoImp
import com.task.praytimes.times.domain.PrayerTimesUseCase
import com.task.praytimes.times.domain.SchedulerUseCase
import com.task.praytimes.times.presentation.alarm.AlarmItem
import com.task.praytimes.times.presentation.alarm.AlarmSchedulerImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission()
        }

        val repo = RepoImp.getInstance(
            RemoteSourceImp.getInstance(),
            LocalSourceImp.getInstance(this)
        )
        val getPrayerTimesUseCase = PrayerTimesUseCase(repo)
        val schedulerUseCase = SchedulerUseCase(repo, getPrayerTimesUseCase)

        lifecycleScope.launch(Dispatchers.IO) {
            schedulerUseCase(2023, 12, 30.9836957, 31.1591778).collectLatest {
                when (it) {
                    is ApiState.Failure -> Log.i("TAG", "failure: ${it.error}")
                    is ApiState.Loading -> Log.i("TAG", "loading")
                    is ApiState.Success -> Log.i("TAG", "success: ${it.data}")
                }
            }
        }

        val scheduler = AlarmSchedulerImp(this)
        scheduler.schedule(
            AlarmItem(
                LocalDateTime.of(
                    2023,
                    12,
                    27,
                    18,
                    27
                ),
                "Isha"
            )
        )
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.POST_NOTIFICATIONS
                ), Constants.NOTIFICATION_PERMISSION_ID
            )
        }
    }
}