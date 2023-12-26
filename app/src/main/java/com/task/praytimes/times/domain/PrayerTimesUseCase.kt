package com.task.praytimes.times.domain

import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.repo.Repo
import com.task.praytimes.times.presentation.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrayerTimesUseCase(
    private val repo: Repo
) {
    operator fun invoke(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<List<PrayerTimes>>> {
        return repo.getPrayerTimes(year, month, latitude, longitude)
            .map { state ->
                if (state is ApiState.Success) {
                    ApiState.Success(getSevenDaysTimes(state.data))
                } else {
                    state
                }
            }
    }

    private fun getSevenDaysTimes(list: List<PrayerTimes>): List<PrayerTimes> {
        val indexOfCurrentDay = list.indexOfFirst { it.date == repo.getCurrentDate() }
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