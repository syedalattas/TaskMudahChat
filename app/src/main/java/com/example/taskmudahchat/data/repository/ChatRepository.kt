package com.example.taskmudahchat.data.repository

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.source.remote.ResponseWrapper

interface ChatRepository {
    fun getChats(): LiveData<List<Chat>>

    suspend fun sendMessage(message: String?): ResponseWrapper<SendResponse>
}