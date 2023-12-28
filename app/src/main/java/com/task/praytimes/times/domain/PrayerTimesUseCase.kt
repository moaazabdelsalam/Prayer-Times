package com.task.praytimes.times.domain

import android.util.Log
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.repo.Repo
import com.task.praytimes.times.presentation.models.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PrayerTimesUseCase @Inject constructor(
    private val repo: Repo
) {
    private val TAG = "TAG PrayerTimesUseCase"

    suspend operator fun invoke(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<List<PrayerTimes>>> {
        return flow {
            emit(ApiState.Loading)
            val localList = repo.getLocalPrayerTimes()
            if (localList.isNotEmpty()) {
                Log.i(TAG, "loading local data:")
                emit(ApiState.Success(localList))
            } else {
                Log.i(TAG, "requesting data from network: ")
                val remoteState = repo.getPrayerTimes(year, month, latitude, longitude)
                if (remoteState is ApiState.Success) {
                    val sevenDaysList = getSevenDaysTimes(remoteState.data)
                    emit(ApiState.Success(sevenDaysList))
                    repo.addPrayerTimesToLocal(sevenDaysList)
                } else {
                    emit(remoteState)
                }
            }
        }
    }

    private fun getSevenDaysTimes(list: List<PrayerTimes>): List<PrayerTimes> {
        val indexOfCurrentDay = list.indexOfFirst { it.dateReadable == repo.getCurrentDate() }
        val startIndex = if (indexOfCurrentDay == -1) 0 else indexOfCurrentDay
        val lastIndex = if ((startIndex + 7) > list.size)
            list.size
        else
            startIndex + 7
        return list.subList(
            startIndex,
            lastIndex
        )
    }
}