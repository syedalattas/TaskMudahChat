package com.example.taskmudahchat.data.repository.data.source

import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.source.remote.RemoteSource
import retrofit2.Response

class FakeRemoteSource : RemoteSource {
    override suspend fun sendMessage(message: String): Response<SendResponse> {
        return Response.success(SendResponse(message, "id", createdAt = "time"))
    }
}