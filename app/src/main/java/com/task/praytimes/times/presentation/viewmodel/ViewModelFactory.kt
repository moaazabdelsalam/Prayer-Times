package com.task.praytimes.times.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.praytimes.times.domain.PrayerTimesUseCase
import com.task.praytimes.times.domain.SchedulerUseCase

class ViewModelFactory(
    private val prayerTimesUseCase: PrayerTimesUseCase,
    private val schedulerUseCase: SchedulerUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(prayerTimesUseCase, schedulerUseCase) as T
        } else
            throw Exception("cant create favorite view model")
    }
}