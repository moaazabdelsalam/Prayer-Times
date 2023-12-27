package com.task.praytimes.times.domain

import android.util.Log
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.repo.Repo
import com.task.praytimes.times.presentation.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SchedulerUseCase(
    private val repo: Repo,
    private val prayerTimesUseCase: PrayerTimesUseCase
) {
    private val TAG = "TAG SchedulerUseCase"

    operator fun invoke(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<List<PrayerTimes>>> {
        return flow {
            emit(ApiState.Loading)
            if (isTimePassed()) {
                Log.i(TAG, "requesting data from network: ")
                emit(makeNetworkCall(year, month, latitude, longitude))
            } else {
                Log.i(TAG, "loading local data:")
                val localList = repo.getLocalPrayerTimes()
                if (localList.isEmpty()) {
                    Log.i(TAG, "empty data requesting from network: ")
                    emit(makeNetworkCall(year, month, latitude, longitude))
                } else {
                    emit(ApiState.Success(localList))
                }
            }
        }
    }

    private suspend fun isTimePassed(): Boolean {
        val latestStoredDate = repo.getLatestStoredDate()
        latestStoredDate ?: return false
        val currentTimeMillis = System.currentTimeMillis()
        val storedTimeMillis = latestStoredDate.time
        return currentTimeMillis > storedTimeMillis
    }

    private suspend fun makeNetworkCall(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ApiState<List<PrayerTimes>> {
        val state: ApiState<List<PrayerTimes>> =
            prayerTimesUseCase(year, month, latitude, longitude)
        return if (state is ApiState.Success) {
            repo.addPrayerTimesToLocal(state.data)
            state
        } else {
            state
        }
    }
}