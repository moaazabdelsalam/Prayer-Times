package com.task.praytimes.times.data.repo

import com.task.praytimes.times.data.local.db.LocalPrayerTimes
import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import com.task.praytimes.times.presentation.models.PrayerTimes

fun PrayerTimesResponse.convertToPrayerTimes(): List<PrayerTimes> {
    return this.timingsData.map {
        PrayerTimes(
            date = it.date.gregorian.date,
            dateReadable = it.date.readable,
            hijriDate = "${it.date.hijri.day} ${it.date.hijri.month.ar} ${it.date.hijri.year}",
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

fun List<PrayerTimes>.convertToLocalPrayerTimes(): List<LocalPrayerTimes> {
    return map {
        LocalPrayerTimes(
            date = it.date,
            dateReadable = it.dateReadable,
            hijriDate = it.hijriDate,
            weekDay = it.weekDay,
            fajr = it.fajr,
            sunrise = it.sunrise,
            dhuhr = it.dhuhr,
            asr = it.asr,
            sunset = it.sunset,
            maghrib = it.maghrib,
            isha = it.isha,
            imsak = it.imsak,
            midnight = it.midnight,
            firstThird = it.firstThird,
            lastThird = it.lastThird
        )
    }
}

fun List<LocalPrayerTimes>.convertToPrayerTimes(): List<PrayerTimes> {
    return map {
        PrayerTimes(
            date = it.date,
            dateReadable = it.dateReadable,
            hijriDate = it.hijriDate,
            weekDay = it.weekDay,
            fajr = it.fajr,
            sunrise = it.sunrise,
            dhuhr = it.dhuhr,
            asr = it.asr,
            sunset = it.sunset,
            maghrib = it.maghrib,
            isha = it.isha,
            imsak = it.imsak,
            midnight = it.midnight,
            firstThird = it.firstThird,
            lastThird = it.lastThird
        )
    }
}