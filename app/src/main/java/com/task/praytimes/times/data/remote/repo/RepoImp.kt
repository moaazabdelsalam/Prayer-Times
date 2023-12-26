package com.task.praytimes.times.data.remote.repo

import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.RemoteSource
import com.task.praytimes.times.data.remote.model.PrayerTimesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RepoImp private constructor(
    private val remoteSource: RemoteSource
) : Repo {

    companion object {
        @Volatile
        private var instance: RepoImp? = null
        fun getInstance(remoteSource: RemoteSource): RepoImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoImp(remoteSource)
                instance = instanceHolder
                instanceHolder

            }
        }
    }

    override fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<PrayerTimesResponse>> {
        return flow {
            emit(ApiState.Loading)
            val result = remoteSource.getPrayerTimes(year, month, latitude, longitude)
            if (result.isSuccessful) {
                result.body()?.let {
                    emit(ApiState.Success(it))
                } ?: emit(ApiState.Failure("Null Response"))
            } else {
                emit(ApiState.Failure("Failure Response: ${result.message()}"))
            }
        }.catch {
            emit(ApiState.Failure("Exception: ${it.message}"))
        }
    }

}