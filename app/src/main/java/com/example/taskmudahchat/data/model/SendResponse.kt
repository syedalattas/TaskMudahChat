package com.example.taskmudahchat.data.model

import com.squareup.moshi.Json

data class SendResponse(
    @field: Json(name = "message")
    val message: String?,
    @field: Json(name = "id")
    val id: String?,
    @field: Json(name = "createdAt")
    val createdAt: String?
)
