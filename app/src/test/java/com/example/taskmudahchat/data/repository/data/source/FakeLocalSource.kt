package com.example.taskmudahchat.data.repository.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.source.local.LocalSource

class FakeLocalSource(private var chats: MutableList<Chat>? = mutableListOf()) : LocalSource {

    private val observableChats = MutableLiveData<List<Chat>>()

    fun addChats(chats: MutableList<Chat>?) {
        this.chats = chats
    }

    override fun getChats(): LiveData<List<Chat>> {
        observableChats.value = chats
        return observableChats
    }

    override suspend fun addChat(chat: Chat) {
        chats?.add(chat)
        observableChats.value = chats
    }
}