package com.task.praytimes.times.data.remote

import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSourceImp @Inject constructor(
    private val service: PrayerTimesService
) : RemoteSource {

    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Response<PrayerTimesResponse> {
        return service.getPrayerTimes(year, month, latitude, longitude)
    }
}