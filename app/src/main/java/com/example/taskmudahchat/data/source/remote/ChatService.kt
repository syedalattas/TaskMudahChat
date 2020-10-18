package com.example.taskmudahchat.data.source.remote

import com.example.taskmudahchat.data.model.SendResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ChatService {
    @FormUrlEncoded
    @POST("users")
    suspend fun sendMessage(@Field("message") message: String): Response<SendResponse>
}