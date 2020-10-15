package com.example.taskmudahchat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.repository.BaseRepository

class ChatViewModel @ViewModelInject constructor(chatRepository: BaseRepository) :
    ViewModel() {

    val chats: LiveData<List<Chat>> = chatRepository.getChats()
}