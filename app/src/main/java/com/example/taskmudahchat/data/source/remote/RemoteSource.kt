package com.example.taskmudahchat.data.source.remote

import com.example.taskmudahchat.data.SendResponse
import retrofit2.Response

class RemoteSource(private val chatService: ChatService) {
    suspend fun sendMessage(message: String): Response<SendResponse> =
        chatService.sendMessage(message)
}