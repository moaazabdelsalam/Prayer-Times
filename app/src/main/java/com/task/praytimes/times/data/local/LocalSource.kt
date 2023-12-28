package com.task.praytimes.times.data.local

import com.task.praytimes.times.data.local.db.LocalPrayerTimes

interface LocalSource {
    fun getCurrentDate(): String
    suspend fun getLocalPrayerTimes(): List<LocalPrayerTimes>
    suspend fun addPrayerTimesToLocal(prayerTimes: List<LocalPrayerTimes>)
    suspend fun deleteAllLocal()
}