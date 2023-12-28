package com.task.praytimes.repo

import com.task.praytimes.datasource.FakeData
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.repo.Repo
import com.task.praytimes.times.data.repo.convertToPrayerTimes
import com.task.praytimes.times.presentation.models.PrayerTimes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale


class RepoTest {
    private lateinit var repo: Repo

    @Before
    fun setup() {
        repo = FakeRepo()
    }

    @Test
    fun getPrayerTimes_success() = runBlocking {
        var data: List<PrayerTimes>? = null
        val job = launch {
            val state = repo.getPrayerTimes(2023, 12, 30.12, 31.23)
            if (state is ApiState.Success)
                data = state.data
        }
        delay(1000L)
        assert(data == FakeData.fakePrayerTimesResponse.convertToPrayerTimes())
        job.cancel()
    }

    @Test
    fun getLatestStoredDate_not_null() = runBlocking {
        val storedDate = repo.getLatestStoredDate()
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val expected = sdf.parse("29 Nov 2023")
        assert(storedDate == expected)
    }

    @Test
    fun getLocalPrayerTimes() = runBlocking {
        val stored = repo.getLocalPrayerTimes()
        val expected = FakeData.fakeLocalPrayerTimes.convertToPrayerTimes()
        assert(stored == expected)
    }
}