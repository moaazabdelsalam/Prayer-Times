package com.task.praytimes.times.data.remote.repo

import com.task.praytimes.times.data.local.LocalSource
import com.task.praytimes.times.data.remote.ApiState
import com.task.praytimes.times.data.remote.RemoteSource
import com.task.praytimes.times.presentation.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.Date

class RepoImp private constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {

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

    override fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<List<PrayerTimes>>> {
        return flow {
            emit(ApiState.Loading)
            val result = remoteSource.getPrayerTimes(year, month, latitude, longitude)
            if (result.isSuccessful) {
                result.body()?.let {
                    emit(ApiState.Success(it.convertToPrayerTimes()))
                } ?: emit(ApiState.Failure("Null Response"))
            } else {
                emit(ApiState.Failure("Failure Response: ${result.message()}"))
            }
        }.catch {
            emit(ApiState.Failure("Exception: ${it.message}"))
        }
    }

    override fun getCurrentDate(): String {
        return localSource.getCurrentDate()
    }

    override fun getStoredDate(): Date? {
        return localSource.getStoredDate()
    }
}