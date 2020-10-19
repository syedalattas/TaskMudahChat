package com.example.taskmudahchat.data.source.remote

import com.example.taskmudahchat.data.model.SendResponse
import retrofit2.Response

class RemoteSourceImpl(private val chatService: ChatService) : RemoteSource {
    override suspend fun sendMessage(message: String): Response<SendResponse> =
        chatService.sendMessage(message)
}