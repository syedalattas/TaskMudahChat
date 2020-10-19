package com.example.taskmudahchat.data.source.remote

import com.example.taskmudahchat.data.model.SendResponse

interface RemoteSource {
    suspend fun sendMessage(message: String): ResponseWrapper<SendResponse>
}