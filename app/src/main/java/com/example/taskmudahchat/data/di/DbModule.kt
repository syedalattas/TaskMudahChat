package com.example.taskmudahchat.data.di

import android.content.Context
import androidx.room.Room
import com.example.taskmudahchat.data.source.local.ChatDao
import com.example.taskmudahchat.data.source.local.ChatDb
import com.example.taskmudahchat.data.source.local.LocalSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): ChatDb {
        return Room.databaseBuilder(
            context,
            ChatDb::class.java,
            "chat.db"
        )
            .createFromAsset("database/chat.db")
            .build()
    }

    @Provides
    fun provideChatDao(chatDb: ChatDb): ChatDao = chatDb.chatDao()

    @Provides
    fun provideLocalSource(chatDao: ChatDao): LocalSource = LocalSource(chatDao)

}