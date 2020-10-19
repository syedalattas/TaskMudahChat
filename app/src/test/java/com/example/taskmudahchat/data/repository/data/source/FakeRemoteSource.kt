package com.example.taskmudahchat.data.repository.data.source

import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.source.remote.RemoteSource
import com.example.taskmudahchat.data.source.remote.ResponseWrapper

class FakeRemoteSource : RemoteSource {
    override suspend fun sendMessage(message: String): ResponseWrapper<SendResponse> {
        return ResponseWrapper.Success(SendResponse(message, "id", createdAt = "time"))
    }
}