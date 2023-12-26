package com.task.praytimes.times.data.remote.model


import com.google.gson.annotations.SerializedName

data class TimingsData(
    @SerializedName("date")
    val date: Date,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("timings")
    val timings: Timings
)