package com.example.taskmudahchat.data.source.local

import com.example.taskmudahchat.data.model.Chat
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun getChats(): Flow<List<Chat>>

    suspend fun addChat(chat: Chat)
}