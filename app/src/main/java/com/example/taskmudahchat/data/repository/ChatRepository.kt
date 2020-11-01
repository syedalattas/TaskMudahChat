package com.example.taskmudahchat.data.repository

import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.source.remote.ResponseWrapper
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChats(): Flow<List<Chat>>

    suspend fun sendMessage(message: String?): ResponseWrapper<SendResponse>
}