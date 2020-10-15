package com.example.taskmudahchat.data.repository

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.SendResponse
import com.example.taskmudahchat.data.source.local.LocalSource
import com.example.taskmudahchat.data.source.remote.RemoteSource

class ChatRepository(private val localSource: LocalSource, private val remoteSource: RemoteSource) :
    BaseRepository {
    override fun getChats(): LiveData<List<Chat>> = localSource.getChats()

    override suspend fun sendMessage(message: String) {
        val result = remoteSource.sendMessage(message)

        if (result.isSuccessful) {
            val newChat: SendResponse? = result.body()
            localSource.addChat(Chat(newChat?.createdAt!!, "OUTGOING", newChat.message))
        }
    }
}