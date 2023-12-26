package com.task.praytimes.times.data.remote.repo

import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import com.task.praytimes.times.presentation.PrayerTimes

fun PrayerTimesResponse.convertToPrayerTimes(): List<PrayerTimes> {
    return this.timingsData.map {
        PrayerTimes(
            date = it.date.readable,
            hijriDate = "${it.date.hijri.year}-${it.date.hijri.month.ar}-${it.date.hijri.day}",
            weekDay = it.date.hijri.weekday.ar,
            fajr = it.timings.fajr,
            sunrise = it.timings.sunrise,
            dhuhr = it.timings.dhuhr,
            asr = it.timings.asr,
            sunset = it.timings.sunset,
            maghrib = it.timings.maghrib,
            isha = it.timings.isha,
            imsak = it.timings.imsak,
            midnight = it.timings.midnight,
            firstThird = it.timings.firstThird,
            lastThird = it.timings.lastThird
        )
    }
}