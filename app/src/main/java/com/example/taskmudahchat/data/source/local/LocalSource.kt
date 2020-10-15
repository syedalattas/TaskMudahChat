package com.example.taskmudahchat.data.source.local

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.source.BaseSource

class LocalSource(private val chatDao: ChatDao) : BaseSource {
    override fun getChats(): LiveData<List<Chat>> = chatDao.getChats()
}