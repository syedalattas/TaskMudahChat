package com.example.taskmudahchat.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.repository.ChatRepository

class ChatViewModel : ViewModel() {

    val chats: LiveData<List<Chat>> = liveData {
        val chatRepository = ChatRepository()
        emit(chatRepository.getChats())
    }
}