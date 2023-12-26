package com.task.praytimes.times.data.remote

import com.task.praytimes.times.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val prayerTimesService: PrayerTimesService by lazy {
        retrofit.create(PrayerTimesService::class.java)
    }
}