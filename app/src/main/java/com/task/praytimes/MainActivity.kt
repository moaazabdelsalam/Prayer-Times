package com.task.praytimes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.task.praytimes.times.data.local.LocalSourceImp
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.RemoteSourceImp
import com.task.praytimes.times.data.remote.repo.RepoImp
import com.task.praytimes.times.domain.PrayerTimesUseCase
import com.task.praytimes.times.domain.SchedulerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}