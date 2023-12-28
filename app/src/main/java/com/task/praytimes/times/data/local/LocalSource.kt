package com.task.praytimes.times.data.local

import com.task.praytimes.times.data.local.db.LocalPrayerTimes
import java.util.Date

interface LocalSource {

    fun getStoredDateString(): String?
    fun getStoredDate(): Date?
    fun getCurrentDate(): String
    suspend fun getLocalPrayerTimes(): List<LocalPrayerTimes>
    suspend fun addPrayerTimesToLocal(prayerTimes: List<LocalPrayerTimes>)
    suspend fun deleteAllLocal()
}