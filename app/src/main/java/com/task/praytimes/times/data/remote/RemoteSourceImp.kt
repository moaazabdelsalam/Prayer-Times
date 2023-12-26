package com.task.praytimes.times.data.remote

import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import retrofit2.Response

class RemoteSourceImp private constructor(): RemoteSource {
    private val network = Network.prayerTimesService

    companion object {
        @Volatile
        private var instance: RemoteSourceImp? = null
        fun getInstance(): RemoteSourceImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RemoteSourceImp()
                instance = instanceHolder
                instanceHolder
            }
        }
    }

    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Response<PrayerTimesResponse> {
        return network.getPrayerTimes(year, month, latitude, longitude)
    }
}