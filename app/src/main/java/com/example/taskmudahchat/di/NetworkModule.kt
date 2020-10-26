package com.example.taskmudahchat.di

import com.example.taskmudahchat.data.source.remote.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://reqres.in/api/ "

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideChatService(retrofit: Retrofit): ChatService =
        retrofit.create(ChatService::class.java)
}