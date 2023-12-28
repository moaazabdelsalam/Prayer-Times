package com.task.praytimes.times.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.domain.PrayerTimesUseCase
import com.task.praytimes.times.domain.SchedulerUseCase
import com.task.praytimes.times.presentation.PrayerTimes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prayerTimesUseCase: PrayerTimesUseCase,
    private val schedulerUseCase: SchedulerUseCase
) : ViewModel() {

    private val _prayerTimesState: MutableStateFlow<ApiState<List<PrayerTimes>>> =
        MutableStateFlow(ApiState.Loading)
    val prayerTimesState: StateFlow<ApiState<List<PrayerTimes>>>
        get() = _prayerTimesState

    init {
        viewModelScope.launch(Dispatchers.IO) { schedulerUseCase() }
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