package com.task.praytimes.times.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalPrayerTimes::class], version = 1, exportSchema = false)
abstract class PrayerTimesDatabase : RoomDatabase() {
    abstract fun prayerDao(): PrayerTimesDao
}