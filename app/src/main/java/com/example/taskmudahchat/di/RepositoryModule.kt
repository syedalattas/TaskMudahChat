package com.example.taskmudahchat.di

import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.repository.ChatRepositoryImpl
import com.example.taskmudahchat.data.source.local.LocalSource
import com.example.taskmudahchat.data.source.remote.ChatService
import com.example.taskmudahchat.data.source.remote.RemoteSource
import com.example.taskmudahchat.data.source.remote.RemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideChatRepository(
        localSourceImpl: LocalSource,
        remoteSourceImpl: RemoteSource
    ): ChatRepository =
        ChatRepositoryImpl(localSourceImpl, remoteSourceImpl)

    @Provides
    fun provideRemoteSource(chatService: ChatService): RemoteSource = RemoteSourceImpl(chatService)
}