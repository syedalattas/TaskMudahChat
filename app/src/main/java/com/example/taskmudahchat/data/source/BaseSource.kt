package com.example.taskmudahchat.data.source

import androidx.lifecycle.LiveData
import com.example.taskmudahchat.data.Chat

interface BaseSource {

    fun getChats(): LiveData<List<Chat>>
}