package com.task.praytimes.times.presentation

import com.google.gson.annotations.SerializedName

data class PrayerTimes(
    val date: String,
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
