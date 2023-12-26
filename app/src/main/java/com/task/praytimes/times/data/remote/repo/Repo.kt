package com.task.praytimes.times.data.remote.repo

import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<PrayerTimesResponse>>
}