package com.example.taskmudahchat.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.repository.data.source.FakeLocalSource
import com.example.taskmudahchat.data.repository.data.source.FakeRemoteSource
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

    // rule to test liveData
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // some instances
    private lateinit var chatRepository: ChatRepository
    private lateinit var localSource: FakeLocalSource
    private lateinit var remoteSource: FakeRemoteSource

    @Before
    fun createRepository() {
        localSource = FakeLocalSource()
        remoteSource = FakeRemoteSource()
        chatRepository = ChatRepositoryImpl(localSource, remoteSource)
    }


    @Test
    fun getChat_nonEmptyList_shouldNotBeNull() {

        // assuming a set of data exist in db as below
        localSource.addChats(
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

        // assuming there is no data in db, when getting all chats
        val result = chatRepository.getChats()

        // then chats should be empty instead of null
        assertThat(result.value, `is`(notNullValue()))
    }

    @Test
    fun getChat_null_returnEmpty() {

        // assuming db returns null
        localSource.addChats(null)

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
        val result = remoteSource.sendMessage(message)

        // then response should be successful
        assertThat(result.isSuccessful, `is`(true))
    }

}