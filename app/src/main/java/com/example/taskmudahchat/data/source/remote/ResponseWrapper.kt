package com.example.taskmudahchat.data.source.remote

sealed class ResponseWrapper<T>(
    val data: T?,
    val message: String? = null
) {
    class Success<T>(data: T?) : ResponseWrapper<T>(data)
    class Error<T>(message: String?) : ResponseWrapper<T>(null, message)
}