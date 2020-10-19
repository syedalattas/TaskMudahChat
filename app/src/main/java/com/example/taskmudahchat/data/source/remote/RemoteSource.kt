package com.example.taskmudahchat.data.source.remote

import com.example.taskmudahchat.data.model.SendResponse
import retrofit2.Response

interface RemoteSource {
    suspend fun sendMessage(message: String): Response<SendResponse>
}