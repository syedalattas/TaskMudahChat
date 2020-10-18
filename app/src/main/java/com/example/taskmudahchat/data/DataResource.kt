package com.example.taskmudahchat.data

sealed class DataResource(
    val message: String? = null
) {
    class Success : DataResource()
    class Error(message: String?) : DataResource(message)
}