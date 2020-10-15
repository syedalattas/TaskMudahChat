package com.example.taskmudahchat.data.di

import com.example.taskmudahchat.data.repository.BaseRepository
import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.source.BaseSource
import com.example.taskmudahchat.data.source.local.ChatDao
import com.example.taskmudahchat.data.source.local.LocalSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideChatRepository(localSource: BaseSource): BaseRepository =
        ChatRepository(localSource)

    @Provides
    fun provideLocalSource(chatDao: ChatDao): BaseSource = LocalSource(chatDao)
}