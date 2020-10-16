package com.example.taskmudahchat.data.di

import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.repository.ChatRepositoryImpl
import com.example.taskmudahchat.data.source.local.LocalSource
import com.example.taskmudahchat.data.source.remote.ChatService
import com.example.taskmudahchat.data.source.remote.RemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideChatRepository(
        localSource: LocalSource,
        remoteSource: RemoteSource
    ): ChatRepository =
        ChatRepositoryImpl(localSource, remoteSource)

    @Provides
    fun provideRemoteSource(chatService: ChatService): RemoteSource = RemoteSource(chatService)
}