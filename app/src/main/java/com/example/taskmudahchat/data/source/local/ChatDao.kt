package com.example.taskmudahchat.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskmudahchat.data.model.Chat
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getChats(): Flow<List<Chat>>

    @Insert
    suspend fun addChat(chat: Chat)
}