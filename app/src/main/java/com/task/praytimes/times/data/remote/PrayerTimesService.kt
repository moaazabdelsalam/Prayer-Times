package com.task.praytimes.times.data.remote


import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerTimesService {

    @GET("{year}/{month}")
    suspend fun getPrayerTimes(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<PrayerTimesResponse>
}