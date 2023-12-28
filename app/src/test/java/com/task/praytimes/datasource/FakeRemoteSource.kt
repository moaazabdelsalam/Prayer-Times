package com.task.praytimes.datasource

import com.task.praytimes.times.data.remote.RemoteSource
import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import retrofit2.Response

class FakeRemoteSource: RemoteSource {
    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Response<PrayerTimesResponse> {
        return Response.success(FakeData.fakePrayerTimesResponse)
    }
}