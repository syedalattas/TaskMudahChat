package com.example.taskmudahchat.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.taskmudahchat.data.Chat

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getChats(): LiveData<List<Chat>>
}