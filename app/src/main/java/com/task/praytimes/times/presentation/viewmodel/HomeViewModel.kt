package com.task.praytimes.times.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.domain.PrayerTimesUseCase
import com.task.praytimes.times.domain.SchedulerUseCase
import com.task.praytimes.times.presentation.PrayerTimes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val prayerTimesUseCase: PrayerTimesUseCase,
    private val schedulerUseCase: SchedulerUseCase
) : ViewModel() {

    private val _prayerTimesState: MutableStateFlow<ApiState<List<PrayerTimes>>> =
        MutableStateFlow(ApiState.Loading)
    val prayerTimesState: StateFlow<ApiState<List<PrayerTimes>>>
        get() = _prayerTimesState

    private val _isScheduled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isScheduled: StateFlow<Boolean>
        get() = _isScheduled
    //var isScheduled: Boolean = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val scheduled = schedulerUseCase()
            withContext(Dispatchers.Main) {
                _isScheduled.value = scheduled
            }
        }
    }

    fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            prayerTimesUseCase(
                year,
                month,
                latitude,
                longitude
            ).collectLatest {
                _prayerTimesState.value = it
            }
        }
    }
}