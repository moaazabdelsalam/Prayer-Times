package com.task.praytimes.times.data.local

import android.content.Context
import com.task.praytimes.times.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LocalSourceImp private constructor(
    context: Context
) : LocalSource {
    private val sharedPreferences = PrayerSharedPreferences(context).mPreference()

    companion object {
        @Volatile
        private var instance: LocalSourceImp? = null
        fun getInstance(context: Context): LocalSourceImp {
            return instance ?: synchronized(this) {
                val instanceHolder = LocalSourceImp(context)
                instance = instanceHolder
                instanceHolder
            }
        }
    }

    override fun getStoredDateString(): String? {
        val storedDateString = sharedPreferences.lastUpdateDate
        return if (storedDateString != Constants.NO_STORED_DATE)
            storedDateString
        else
            null
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

}