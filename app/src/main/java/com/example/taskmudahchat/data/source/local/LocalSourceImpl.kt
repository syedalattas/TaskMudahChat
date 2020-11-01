package com.example.taskmudahchat.data.source.local

import com.example.taskmudahchat.data.model.Chat
import kotlinx.coroutines.flow.Flow

class LocalSourceImpl(private val chatDao: ChatDao) : LocalSource {
    override fun getChats(): Flow<List<Chat>> = chatDao.getChats()

    override suspend fun addChat(chat: Chat) {
        chatDao.addChat(chat)
    }
}