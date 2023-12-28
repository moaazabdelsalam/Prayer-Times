package com.task.praytimes.times.domain

import android.util.Log
import com.task.praytimes.times.data.repo.Repo
import javax.inject.Inject

class SchedulerUseCase @Inject constructor(
    private val repo: Repo,
) {
    private val TAG = "TAG SchedulerUseCase"

    suspend operator fun invoke(): Boolean {
        Log.i(TAG, "isTimePassed: ${isTimePassed()}")
        return if (isTimePassed()) {
            repo.deleteAllLocal()
            false
        } else {
            true
        }
    }

    private suspend fun isTimePassed(): Boolean {
        val latestStoredDate = repo.getLatestStoredDate()
        latestStoredDate ?: return true
        val currentTimeMillis = System.currentTimeMillis()
        val storedTimeMillis = latestStoredDate.time
        return currentTimeMillis > storedTimeMillis
    }
}