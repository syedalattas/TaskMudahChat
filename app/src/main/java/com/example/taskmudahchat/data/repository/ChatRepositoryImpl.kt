package com.example.taskmudahchat.data.repository

import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.source.local.LocalSource
import com.example.taskmudahchat.data.source.remote.RemoteSource
import com.example.taskmudahchat.data.source.remote.ResponseWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class ChatRepositoryImpl(
    private val localSourceImpl: LocalSource,
    private val remoteSourceImpl: RemoteSource
) :
    ChatRepository {

    // get all chat from db
    override suspend fun getChats(): Flow<List<Chat>> {
        return localSourceImpl.getChats()
    }

    // send message via api and store message to db on success
    override suspend fun sendMessage(message: String?): ResponseWrapper<SendResponse> {

        // verify message is not null
        if (message.isNullOrEmpty()) {
            return ResponseWrapper.Error("Message cannot be empty")
        }

        val result = remoteSourceImpl.sendMessage(message)
        if (result is ResponseWrapper.Success) {
            val response: SendResponse? = result.data
            val newChat = Chat(response?.createdAt!!, "OUTGOING", response.message)
            localSourceImpl.addChat(newChat)
        }

        // return to let UI handle if there is any error from the API
        return result
    }
}