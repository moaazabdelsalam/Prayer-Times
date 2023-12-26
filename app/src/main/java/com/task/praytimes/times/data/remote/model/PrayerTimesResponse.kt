package com.task.praytimes.times.data.remote.model


import com.google.gson.annotations.SerializedName

data class PrayerTimesResponse(
    val code: Int,
    @SerializedName("data")
    val timingsData: List<TimingsData>,
    val status: String
)