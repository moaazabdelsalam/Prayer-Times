package com.task.praytimes.times.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerTimesDao {
    @Query("SELECT * FROM prayer_times")
    suspend fun getAll(): List<LocalPrayerTimes>

    @Query("DELETE FROM prayer_times")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(prayerTimes: List<LocalPrayerTimes>)
}