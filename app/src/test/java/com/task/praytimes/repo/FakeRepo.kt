package com.task.praytimes.repo

import com.task.praytimes.datasource.FakeLocalSource
import com.task.praytimes.datasource.FakeRemoteSource
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.repo.Repo
import com.task.praytimes.times.data.repo.convertToLocalPrayerTimes
import com.task.praytimes.times.data.repo.convertToPrayerTimes
import com.task.praytimes.times.presentation.models.PrayerTimes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FakeRepo : Repo {
    private val localSource = FakeLocalSource()
    private val remoteSource = FakeRemoteSource()

    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ApiState<List<PrayerTimes>> {
        return ApiState.Success(
            remoteSource
                .getPrayerTimes(year, month, latitude, longitude)
                .body()!!
                .convertToPrayerTimes()
        )
    }

    override suspend fun getLatestStoredDate(): Date? {
        val lastSavedPrayerTimes = getLocalPrayerTimes().lastOrNull() ?: return null
        return try {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            sdf.parse(lastSavedPrayerTimes.dateReadable)
        } catch (e: Exception) {
            null
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

    override suspend fun deleteAllLocal() {
        localSource.deleteAllLocal()
    }
}