package com.example.taskmudahchat.data

data class DataResource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }

    companion object {
        fun <T> success(data: T): DataResource<T> = DataResource(Status.SUCCESS, data, null)
        fun <T> loading(data: T? = null): DataResource<T> = DataResource(Status.LOADING, data, null)
        fun <T> error(message: String, data: T? = null) = DataResource(Status.ERROR, data, message)
    }
}