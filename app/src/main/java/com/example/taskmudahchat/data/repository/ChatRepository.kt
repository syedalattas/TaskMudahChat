package com.example.taskmudahchat.data.repository

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.source.BaseSource

class ChatRepository(private val localSource: BaseSource) : BaseRepository {
    override fun getChats(): LiveData<List<Chat>> = localSource.getChats()
}