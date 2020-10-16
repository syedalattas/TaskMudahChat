package com.example.taskmudahchat.data

data class DataResource(
    val status: Status,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun success(): DataResource = DataResource(Status.SUCCESS, null)
        fun error(message: String) = DataResource(Status.ERROR, message)
    }
}