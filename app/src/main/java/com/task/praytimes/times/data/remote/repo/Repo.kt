package com.task.praytimes.times.data.remote.repo

import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.presentation.PrayerTimes
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface Repo {
    suspend fun getPrayerTimes2(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ApiState<List<PrayerTimes>>
    fun getStoredDate(): Date?
    fun getCurrentDate(): String
    suspend fun getLocalPrayerTimes(): List<PrayerTimes>
    suspend fun addPrayerTimesToLocal(prayerTimes: List<PrayerTimes>)
}