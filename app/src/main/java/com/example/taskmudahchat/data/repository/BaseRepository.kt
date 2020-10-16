package com.example.taskmudahchat.data.repository

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.DataResource

interface BaseRepository {
    fun getChats(): LiveData<List<Chat>>

    suspend fun sendMessage(message: String): DataResource
}