package com.example.taskmudahchat.data.repository

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.DataResource
import com.example.taskmudahchat.data.SendResponse
import com.example.taskmudahchat.data.source.local.LocalSource
import com.example.taskmudahchat.data.source.remote.RemoteSource
import java.lang.Exception

class ChatRepository(private val localSource: LocalSource, private val remoteSource: RemoteSource) :
    BaseRepository {
    override fun getChats(): LiveData<List<Chat>> = localSource.getChats()

    override suspend fun sendMessage(message: String): DataResource {
        return try {
            val result = remoteSource.sendMessage(message)
            if (result.isSuccessful) {
                val response: SendResponse? = result.body()
                val newChat = Chat(response?.createdAt!!, "OUTGOING", response.message)
                localSource.addChat(newChat)
                DataResource.success()
            } else {
                DataResource.error(result.message())
            }
        } catch (e: Exception) {
            DataResource.error(e.message!!)
        }
    }
}