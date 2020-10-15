package com.example.taskmudahchat.data.repository

import com.example.taskmudahchat.data.Chat

class ChatRepository : BaseRepository {
    override fun getChats(): List<Chat> = listOf(
        Chat(
            "2018-05-28T10:00:00.000Z",
            "OUTGOING",
            "Hello"
        ),
        Chat(
            "2018-05-29T11:05:00.000Z",
            "INCOMING",
            "Hi"
        ),
        Chat(
            "2018-06-01T22:00:00.000Z",
            "OUTGOING",
            "What is Mudah.my"
        ),
        Chat(
            "2018-06-02T22:01:00.000Z",
            "INCOMING",
            "Mudah.my is the most favorite local app in Malaysia for searching, buying and selling pre-loved items. Get best deals at the reasonable prices from over 1,6 million items every day."
        ),
        Chat(
            "2018-06-03T09:15:00.000Z",
            "OUTGOING",
            "I see, thanks!"
        ),
        Chat(
            "2018-06-04T08:50:00.000Z",
            "INCOMING",
            "No problem!"
        ),
    )
}