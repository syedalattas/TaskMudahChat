package com.example.taskmudahchat.data.repository.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.source.remote.ResponseWrapper

class FakeChatRepository : ChatRepository {

    private val observable = MutableLiveData<List<Chat>>()

    fun addChats(chats: List<Chat>?) {
        observable.value = chats
    }

    override fun getChats(): LiveData<List<Chat>> {
        return observable
    }

    override suspend fun sendMessage(message: String?): ResponseWrapper<SendResponse> {
        return ResponseWrapper.Success(SendResponse("message", "id", "createdAt"))
    }
}