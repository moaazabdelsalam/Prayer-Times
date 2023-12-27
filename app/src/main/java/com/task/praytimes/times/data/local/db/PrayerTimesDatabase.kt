package com.task.praytimes.times.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.praytimes.times.Constants

@Database(entities = [LocalPrayerTimes::class], version = 1, exportSchema = false)
abstract class PrayerTimesDatabase: RoomDatabase() {
    abstract fun prayerDao(): PrayerTimesDao

    companion object{
        @Volatile
        private  var instance: PrayerTimesDatabase?=null

        fun getInstance(context: Context): PrayerTimesDatabase {
            return instance ?: synchronized(this){
                val instanceHolder=  Room.databaseBuilder(context.applicationContext,
                    PrayerTimesDatabase::class.java,Constants.DATA_BASE).build()
                instance =instanceHolder
                instanceHolder
            }
        }
    }
}