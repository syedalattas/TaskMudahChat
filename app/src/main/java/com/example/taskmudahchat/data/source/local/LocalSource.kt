package com.example.taskmudahchat.data.source.local

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.model.Chat

interface LocalSource {
    fun getChats(): LiveData<List<Chat>>

    suspend fun addChat(chat: Chat)
}