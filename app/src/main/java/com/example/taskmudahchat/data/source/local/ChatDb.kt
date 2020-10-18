package com.example.taskmudahchat.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmudahchat.data.model.Chat

@Database(entities = [Chat::class], version = 1, exportSchema = false)
abstract class ChatDb: RoomDatabase() {

    abstract fun chatDao(): ChatDao
}