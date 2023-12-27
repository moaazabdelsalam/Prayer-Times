package com.task.praytimes.times.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_times")
data class LocalPrayerTimes(
    @PrimaryKey
    val date: String,
    val dateReadable: String,
    val hijriDate: String,
    val weekDay: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val sunset: String,
    val maghrib: String,
    val isha: String,
    val imsak: String,
    val midnight: String,
    val firstThird: String,
    val lastThird: String
)
