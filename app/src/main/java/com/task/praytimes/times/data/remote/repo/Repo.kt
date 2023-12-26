package com.task.praytimes.times.data.remote.repo

import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.presentation.PrayerTimes
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface Repo {
    fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<List<PrayerTimes>>>

    fun getStoredDate(): Date?
    fun getCurrentDate(): String
}