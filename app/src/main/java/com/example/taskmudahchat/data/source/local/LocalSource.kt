package com.example.taskmudahchat.data.source.local

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.model.Chat

class LocalSource(private val chatDao: ChatDao) {
    fun getChats(): LiveData<List<Chat>> = chatDao.getChats()

    suspend fun addChat(chat: Chat) {
        chatDao.addChat(chat)
    }
}