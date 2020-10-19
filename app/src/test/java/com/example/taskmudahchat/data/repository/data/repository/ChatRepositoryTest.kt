package com.example.taskmudahchat.data.repository.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.repository.ChatRepositoryImpl
import com.example.taskmudahchat.data.source.local.LocalSource
import com.example.taskmudahchat.data.source.remote.RemoteSource
import com.example.taskmudahchat.data.source.remote.ResponseWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChatRepositoryTest {

    @get:Rule
    // allows us to test liveData
    var instantExecutorRule = InstantTaskExecutorRule()

    // some instances
    private lateinit var chatRepository: ChatRepository

    @RelaxedMockK
    private lateinit var localSource: LocalSource

    @RelaxedMockK
    private lateinit var remoteSource: RemoteSource

    @Before
    fun createRepository() {
        MockKAnnotations.init(this)
        chatRepository = ChatRepositoryImpl(localSource, remoteSource)
    }

    @Test
    fun getChat_nonEmptyList_shouldNotBeNull() {

        // assuming a set of data exist in db as below
        every { localSource.getChats() } returns MutableLiveData(
            mutableListOf(
                Chat("timestamp", "direction", "message"),
                Chat("timestamp", "direction", "message"),
                Chat("timestamp", "direction", "message"),
            )
        )

        // when getting all chats from db
        val result = chatRepository.getChats()

        // then chats should not return null
        assertThat(result.value, `is`(notNullValue()))
    }

    @Test
    fun getChat_emptyList_returnEmpty() {

        // assuming there is no data in db,
        every { localSource.getChats() } returns MutableLiveData(mutableListOf())

        // when getting all chats
        val result = chatRepository.getChats()

        // then chats should be empty instead of null
        assertThat(result.value, `is`(notNullValue()))
    }

    @Test
    fun sendMessage_notNull_returnSuccess() = runBlockingTest {

        // assuming user send a message as such
        val message = "Message"

        // when sending a message
        coEvery { remoteSource.sendMessage(message) } returns ResponseWrapper.Success(
            SendResponse(
                message,
                "id",
                createdAt = "createdAt"
            )
        )
        val result = chatRepository.sendMessage(message)

        // then response should be successful
        assertThat(result is ResponseWrapper.Success, `is`(true))
    }

    @Test
    fun sendMessage_null_returnError() = runBlockingTest {

        // when sending a null message
        val result = chatRepository.sendMessage(null)

        // then response should be error
        assertThat(result is ResponseWrapper.Error, `is`(true))
        assertThat(result.message, `is`("Message cannot be empty"))
    }

    @Test
    fun sendMessage_empty_returnError() = runBlockingTest {

        // when sending a null message
        val message = ""
        coEvery { remoteSource.sendMessage(message) }
        val result = chatRepository.sendMessage(message)

        // then response should be error
        assertThat(result is ResponseWrapper.Error, `is`(true))
        assertThat(result.message, `is`("Message cannot be empty"))
    }

}