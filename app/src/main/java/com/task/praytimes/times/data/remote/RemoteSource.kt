package com.task.praytimes.times.data.remote

import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import retrofit2.Response

interface RemoteSource {

    suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Response<PrayerTimesResponse>
}