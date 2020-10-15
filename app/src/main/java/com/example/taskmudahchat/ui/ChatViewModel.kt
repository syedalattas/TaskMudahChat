package com.example.taskmudahchat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.repository.BaseRepository
import kotlinx.coroutines.launch

class ChatViewModel @ViewModelInject constructor(private val chatRepository: BaseRepository) :
    ViewModel() {

    val chats: LiveData<List<Chat>> = chatRepository.getChats()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            chatRepository.sendMessage(message)
        }
    }
}