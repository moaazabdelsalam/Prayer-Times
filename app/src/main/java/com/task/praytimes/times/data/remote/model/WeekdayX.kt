package com.task.praytimes.times.data.remote.model


import com.google.gson.annotations.SerializedName

data class WeekdayX(
    @SerializedName("ar")
    val ar: String,
    @SerializedName("en")
    val en: String
)