package com.task.praytimes

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.task.praytimes.times.Constants
import com.task.praytimes.times.data.local.LocalSourceImp
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.RemoteSourceImp
import com.task.praytimes.times.data.repo.RepoImp
import com.task.praytimes.times.domain.PrayerTimesUseCase
import com.task.praytimes.times.domain.SchedulerUseCase
import com.task.praytimes.times.presentation.PrayerTimes
import com.task.praytimes.times.presentation.alarm.AlarmItem
import com.task.praytimes.times.presentation.alarm.AlarmSchedulerImp
import com.task.praytimes.times.presentation.viewmodel.HomeViewModel
import com.task.praytimes.times.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private val TAG = "TAG MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission()
        }

        val viewModelFactory = ViewModelFactory(
            PrayerTimesUseCase(
                RepoImp.getInstance(
                    RemoteSourceImp.getInstance(),
                    LocalSourceImp.getInstance(this)
                )
            ),
            SchedulerUseCase(
                RepoImp.getInstance(
                    RemoteSourceImp.getInstance(),
                    LocalSourceImp.getInstance(this)
                )
            )
        )
        val homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        homeViewModel.getPrayerTimes(2023, 12, 30.9836957, 31.1591778)
        lifecycleScope.launch(Dispatchers.IO) {
            homeViewModel.prayerTimesState.collectLatest {
                when (it) {
                    is ApiState.Failure -> Log.i(TAG, "failure: ${it.error}")
                    is ApiState.Loading -> Log.i(TAG, "loading")
                    is ApiState.Success -> {
                        Log.i(TAG, "success: ${it.data}")
                        scheduleAlarmToPrayerTimes(it.data)
                        /*homeViewModel.isScheduled.collectLatest {isScheduled ->
                            Log.i(TAG, "isScheduled: $isScheduled")
                            if (!isScheduled) {
                                scheduleAlarmToPrayerTimes(it.data)
                            }
                        }*/
                    }
                }
            }
        }
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

    private fun scheduleAlarmToPrayerTimes(prayerTimes: List<PrayerTimes>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val scheduler = AlarmSchedulerImp(this@MainActivity)
            prayerTimes.forEach {
                val date = it.date.split("-")
                val fajrTime = it.fajr.split(":")
                val dhuhrTime = it.dhuhr.split(":")
                val asrTime = it.asr.split(":")
                val maghribTime = it.maghrib.split(":")
                val ishaTime = it.isha.split(":")
                scheduler.schedule(createAlarmItem(date, fajrTime, "Fajr"))
                scheduler.schedule(createAlarmItem(date, dhuhrTime, "Dhuhr"))
                scheduler.schedule(createAlarmItem(date, asrTime, "Asr"))
                scheduler.schedule(createAlarmItem(date, maghribTime, "Maghrib"))
                scheduler.schedule(createAlarmItem(date, ishaTime, "Isha"))
            }
        }
    }

    private fun createAlarmItem(
        date: List<String>,
        time: List<String>,
        timing: String
    ): AlarmItem {
        return AlarmItem(
            LocalDateTime.of(
                date[2].toInt(),
                date[1].toInt(),
                date[0].toInt(),
                time[0].toInt(),
                time[1].split(" ")[0].toInt()
            ),
            timing
        )
    }
}