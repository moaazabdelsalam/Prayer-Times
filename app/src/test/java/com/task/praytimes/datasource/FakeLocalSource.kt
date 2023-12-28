package com.task.praytimes.datasource

import com.task.praytimes.times.data.local.LocalSource
import com.task.praytimes.times.data.local.db.LocalPrayerTimes

class FakeLocalSource: LocalSource {
    override fun getCurrentDate(): String {
        return "2023-11-29"
    }

    override suspend fun getLocalPrayerTimes(): List<LocalPrayerTimes> {
        return FakeData.fakeLocalPrayerTimes
    }

    override suspend fun addPrayerTimesToLocal(prayerTimes: List<LocalPrayerTimes>) {
        FakeData.fakeLocalPrayerTimes = prayerTimes
    }

    override suspend fun deleteAllLocal() {
        FakeData.fakeLocalPrayerTimes = emptyList()
    }
}