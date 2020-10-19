package com.example.taskmudahchat.data.source.remote

import android.util.Log
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResponseWrapper<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResponseWrapper.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ResponseWrapper<T> {
        Log.e("remoteDataSource", message)
        return ResponseWrapper.Error("Network call has failed for a following reason: $message")
    }

}