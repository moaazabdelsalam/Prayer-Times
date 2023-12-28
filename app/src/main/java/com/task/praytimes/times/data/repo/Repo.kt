package com.task.praytimes.times.data.repo

import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.presentation.PrayerTimes
import java.util.Date

interface Repo {
    suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ApiState<List<PrayerTimes>>
    suspend fun getLatestStoredDate(): Date?
    fun getCurrentDate(): String
    suspend fun getLocalPrayerTimes(): List<PrayerTimes>
    suspend fun addPrayerTimesToLocal(prayerTimes: List<PrayerTimes>)
    suspend fun deleteAllLocal()
}