package com.task.praytimes.times.data.repo

import android.util.Log
import com.task.praytimes.times.data.local.LocalSource
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.RemoteSource
import com.task.praytimes.times.presentation.PrayerTimes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoImp @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {
    private val TAG = "TAG RepoIm"

    override suspend fun getPrayerTimes(
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
        val lastSavedPrayerTimes = getLocalPrayerTimes().lastOrNull() ?: return null
        return try {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            sdf.parse(lastSavedPrayerTimes.dateReadable)
        } catch (e: Exception) {
            Log.i(TAG, "getLatestStoredDate: exception ${e.message}")
            null
        }
    }

    override suspend fun deleteAllLocal() {
        localSource.deleteAllLocal()
    }
}