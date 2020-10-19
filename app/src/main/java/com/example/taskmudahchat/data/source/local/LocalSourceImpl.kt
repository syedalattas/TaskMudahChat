package com.example.taskmudahchat.data.source.local

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.model.Chat

class LocalSourceImpl(private val chatDao: ChatDao) : LocalSource {
    override fun getChats(): LiveData<List<Chat>> = chatDao.getChats()

    override suspend fun addChat(chat: Chat) {
        chatDao.addChat(chat)
    }
}