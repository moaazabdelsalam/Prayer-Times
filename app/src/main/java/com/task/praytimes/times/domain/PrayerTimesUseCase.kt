package com.task.praytimes.times.domain

import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.repo.Repo
import com.task.praytimes.times.presentation.PrayerTimes

class PrayerTimesUseCase(
    private val repo: Repo
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ApiState<List<PrayerTimes>> {
        val resultState = repo.getPrayerTimes2(year, month, latitude, longitude)
        return if (resultState is ApiState.Success) {
            ApiState.Success(getSevenDaysTimes(resultState.data))
        } else {
            resultState
        }
    }

    private fun getSevenDaysTimes(list: List<PrayerTimes>): List<PrayerTimes> {
        val indexOfCurrentDay = list.indexOfFirst { it.dateReadable == repo.getCurrentDate() }
        val lastIndex = if ((indexOfCurrentDay + 7) > list.size)
            list.size
        else
            indexOfCurrentDay + 7
        return list.subList(
            indexOfCurrentDay,
            lastIndex
        )
    }
}