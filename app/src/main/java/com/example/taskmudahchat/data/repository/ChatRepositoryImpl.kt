package com.example.taskmudahchat.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmudahchat.data.DataResource
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.source.local.LocalSource
import com.example.taskmudahchat.data.source.remote.RemoteSource

class ChatRepositoryImpl(
    private val localSourceImpl: LocalSource,
    private val remoteSourceImpl: RemoteSource
) :
    ChatRepository {

    // get all chat from db
    override fun getChats(): LiveData<List<Chat>> {
        val result = localSourceImpl.getChats()
        return if (!result.value.isNullOrEmpty()) {
            result
        } else {
            return MutableLiveData(listOf())
        }
    }

    // send message via api and store message to db on success
    override suspend fun sendMessage(message: String): DataResource = try {
        val result = remoteSourceImpl.sendMessage(message)
        if (result.isSuccessful) {
            val response: SendResponse? = result.body()
            val newChat = Chat(response?.createdAt!!, "OUTGOING", response.message)
            localSourceImpl.addChat(newChat)
            DataResource.Success()
        } else {
            DataResource.Error(result.message())
        }
    } catch (e: Exception) {
        DataResource.Error(e.message!!)
    }
}