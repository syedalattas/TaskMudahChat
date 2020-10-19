package com.example.taskmudahchat.data.source.remote

import com.example.taskmudahchat.data.model.SendResponse

class RemoteSourceImpl(private val chatService: ChatService) : RemoteSource, BaseDataSource() {

    // use getResult to encapsulate the states of the response nicely
    override suspend fun sendMessage(message: String): ResponseWrapper<SendResponse> =
        getResult { chatService.sendMessage(message) }
}