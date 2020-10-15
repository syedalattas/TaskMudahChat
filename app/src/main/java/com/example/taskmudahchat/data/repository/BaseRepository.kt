package com.example.taskmudahchat.data.repository

import com.example.taskmudahchat.data.Chat

interface BaseRepository {

    fun getChats(): List<Chat>
}