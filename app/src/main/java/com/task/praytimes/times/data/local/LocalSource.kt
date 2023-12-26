package com.task.praytimes.times.data.local

import java.util.Date

interface LocalSource {

    fun getStoredDateString(): String?
    fun getStoredDate(): Date?
    fun getCurrentDate(): String
}