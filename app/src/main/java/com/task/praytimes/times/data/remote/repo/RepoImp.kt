package com.task.praytimes.times.data.remote.repo

import android.util.Log
import com.task.praytimes.times.data.local.LocalSource
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.RemoteSource
import com.task.praytimes.times.presentation.PrayerTimes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RepoImp private constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {
    private val TAG = "TAG RepoIm"

    companion object {
        @Volatile
        private var instance: RepoImp? = null
        fun getInstance(remoteSource: RemoteSource, localSource: LocalSource): RepoImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoImp(remoteSource, localSource)
                instance = instanceHolder
                instanceHolder

            }
        }
    }

    override suspend fun getPrayerTimes2(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ApiState<List<PrayerTimes>> {
        return try {
            val result = remoteSource.getPrayerTimes(year, month, latitude, longitude)
            if (result.isSuccessful) {
                result.body()?.let {
                    val prayerTimes = it.convertToPrayerTimes()
                    ApiState.Success(prayerTimes)
                } ?: ApiState.Failure("Null Response")
            } else {
                ApiState.Failure("Failure Response: ${result.message()}")
            }
        } catch (e: Exception) {
            ApiState.Failure("Exception: ${e.message}")
        }
    }

    override fun getCurrentDate(): String {
        return localSource.getCurrentDate()
    }

    override suspend fun getLocalPrayerTimes(): List<PrayerTimes> {
        return localSource.getLocalPrayerTimes().convertToPrayerTimes()
    }

    override suspend fun addPrayerTimesToLocal(prayerTimes: List<PrayerTimes>) {
        localSource.addPrayerTimesToLocal(prayerTimes.convertToLocalPrayerTimes())
    }

    override suspend fun getLatestStoredDate(): Date? {
        //return localSource.getStoredDate()
        val lastSavedPrayerTimes = getLocalPrayerTimes().lastOrNull() ?: return null
        return try {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            sdf.parse(lastSavedPrayerTimes.date)
        } catch (e: Exception) {
            Log.i(TAG, "getLatestStoredDate: exception ${e.message}")
            null
        }
    }
}