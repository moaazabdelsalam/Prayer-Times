package com.task.praytimes.times.data.local

import com.task.praytimes.times.data.local.db.LocalPrayerTimes
import com.task.praytimes.times.data.local.db.PrayerTimesDao
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSourceImp @Inject constructor(
    private val prayerTimesDao: PrayerTimesDao
) : LocalSource {

    override fun getStoredDateString(): String? {
        /*val storedDateString = sharedPreferences.lastUpdateDate
        return if (storedDateString != Constants.NO_STORED_DATE)
            storedDateString
        else
            null*/
        return ""
    }

    override fun getStoredDate(): Date? {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        return getStoredDateString()?.let {
            sdf.parse(it)
        }
    }

    override fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        return sdf.format(calendar.time)
    }

    override suspend fun getLocalPrayerTimes(): List<LocalPrayerTimes> {
        return prayerTimesDao.getAll()
    }

    override suspend fun addPrayerTimesToLocal(prayerTimes: List<LocalPrayerTimes>) {
        prayerTimesDao.addAll(prayerTimes)
    }

    override suspend fun deleteAllLocal() {
        prayerTimesDao.deleteAll()
    }
}