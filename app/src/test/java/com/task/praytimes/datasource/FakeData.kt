package com.task.praytimes.datasource

import com.task.praytimes.times.data.local.db.LocalPrayerTimes
import com.task.praytimes.times.data.remote.model.Date
import com.task.praytimes.times.data.remote.model.Designation
import com.task.praytimes.times.data.remote.model.Gregorian
import com.task.praytimes.times.data.remote.model.Hijri
import com.task.praytimes.times.data.remote.model.Location
import com.task.praytimes.times.data.remote.model.Meta
import com.task.praytimes.times.data.remote.model.Method
import com.task.praytimes.times.data.remote.model.Month
import com.task.praytimes.times.data.remote.model.MonthX
import com.task.praytimes.times.data.remote.model.Offset
import com.task.praytimes.times.data.remote.model.Params
import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import com.task.praytimes.times.data.remote.model.Timings
import com.task.praytimes.times.data.remote.model.TimingsData
import com.task.praytimes.times.data.remote.model.Weekday
import com.task.praytimes.times.data.remote.model.WeekdayX

object FakeData {
    val fakePrayerTimesResponse = PrayerTimesResponse(
        code = 200,
        timingsData = listOf(
            TimingsData(
                date = Date(
                    gregorian = Gregorian(
                        date = "2023-11-29",
                        day = "29",
                        designation = Designation(abbreviated = "Nov", expanded = "November"),
                        format = "yyyy-MM-dd",
                        month = Month(en = "November", number = 11),
                        weekday = Weekday(en = "Tuesday"),
                        year = "2023"
                    ),
                    hijri = Hijri(
                        date = "1445-03-25",
                        day = "25",
                        designation = Designation(abbreviated = "Raj", expanded = "Rajab"),
                        format = "yyyy-MM-dd",
                        holidays = emptyList(),
                        month = MonthX(ar = "رجب", en = "Rajab", number = 7),
                        weekday = WeekdayX(ar = "الثلاثاء", en = "Tuesday"),
                        year = "1445"
                    ),
                    readable = "2023-11-29",
                    timestamp = "1677725199000"
                ),
                meta = Meta(
                    latitude = 12.9714,
                    latitudeAdjustmentMethod = "ANGLE_BASED",
                    longitude = 77.5946,
                    method = Method(
                        id = 1,
                        location = Location(latitude = 12.9714, longitude = 77.5946),
                        name = "ISNA",
                        params = Params(fajr = 18.0, isha = 18.0)
                    ),
                    midnightMode = "STANDARD",
                    offset = Offset(
                        asr = 1,
                        dhuhr = 0,
                        fajr = 0,
                        imsak = 10,
                        isha = 0,
                        maghrib = 0,
                        midnight = 0,
                        sunrise = 0,
                        sunset = 0
                    ),
                    school = "ISNA",
                    timezone = "Asia/Kolkata"
                ),
                timings = Timings(
                    asr = "14:30",
                    dhuhr = "12:30",
                    fajr = "05:30",
                    firstThird = "06:00",
                    imsak = "05:20",
                    isha = "18:30",
                    lastThird = "07:00",
                    maghrib = "17:30",
                    midnight = "00:00",
                    sunrise = "06:15",
                    sunset = "17:45"
                )
            )
        ),
        status = "OK"
    )

    var fakeLocalPrayerTimes = listOf(
        LocalPrayerTimes(
            date = "2023-11-22",
            dateReadable = "22 Nov 2023",
            hijriDate = "1445-03-18",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
        LocalPrayerTimes(
            date = "2023-11-23",
            dateReadable = "23 Nov 2023",
            hijriDate = "1445-03-19",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
        LocalPrayerTimes(
            date = "2023-11-24",
            dateReadable = "24 Nov 2023",
            hijriDate = "1445-03-20",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
        LocalPrayerTimes(
            date = "2023-11-25",
            dateReadable = "25 Nov 2023",
            hijriDate = "1445-03-21",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
        LocalPrayerTimes(
            date = "2023-11-26",
            dateReadable = "26 Nov 2023",
            hijriDate = "1445-03-22",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
        LocalPrayerTimes(
            date = "2023-11-27",
            dateReadable = "27 Nov 2023",
            hijriDate = "1445-03-23",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
        LocalPrayerTimes(
            date = "2023-11-28",
            dateReadable = "28 Nov 2023",
            hijriDate = "1445-03-24",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
        LocalPrayerTimes(
            date = "2023-11-29",
            dateReadable = "29 Nov 2023",
            hijriDate = "1445-03-25",
            weekDay = "Saturday",
            asr = "14:30",
            dhuhr = "12:30",
            fajr = "05:30",
            firstThird = "06:00",
            imsak = "05:20",
            isha = "18:30",
            lastThird = "07:00",
            maghrib = "17:30",
            midnight = "00:00",
            sunrise = "06:15",
            sunset = "17:45"
        ),
    )
}