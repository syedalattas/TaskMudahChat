package com.example.taskmudahchat.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class Chat(
    @NonNull
    @PrimaryKey
    val timestamp: String,
    val direction: String?,
    val message: String?
)