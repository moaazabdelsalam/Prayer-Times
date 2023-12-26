package com.task.praytimes.times.data.remote

sealed class ApiState<out T> {
    class Success<T>(val data: T) : ApiState<T>()
    class Failure(val error: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}