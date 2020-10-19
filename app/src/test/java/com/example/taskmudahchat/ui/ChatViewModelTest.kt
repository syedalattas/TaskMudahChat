package com.example.taskmudahchat.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.model.SendResponse
import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.source.remote.ResponseWrapper
import com.example.taskmudahchat.util.CoroutinesTestRule
import com.example.taskmudahchat.util.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChatViewModelTest {

    @get:Rule
    // allows us to test liveData
    var instantExecutorRule = InstantTaskExecutorRule()
    // allows us to test any dispatchers use in a function
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var chatViewModel: ChatViewModel

    @RelaxedMockK
    private lateinit var chatRepository: ChatRepository

    @Before
    fun createViewModel() {

        MockKAnnotations.init(this)
        chatViewModel = ChatViewModel(chatRepository)
    }

    @Test
    fun getChat_nonEmptyList_shouldNotBeNull() {

        every { chatRepository.getChats() } returns MutableLiveData(
            mutableListOf(
                Chat("timestamp", "direction", "message"),
                Chat("timestamp", "direction", "message"),
                Chat("timestamp", "direction", "message"),
            )
        )

        // when view model get chat from repo
        val result = chatViewModel.chats.getOrAwaitValue()

        // then data should not be null
        assertThat(result, `is`(notNullValue()))
    }

    @Test
    fun getChat_empty_shouldReturnEmptyList() {

        // given that repo returns empty
        every { chatRepository.getChats() } returns MutableLiveData(mutableListOf())

        // when view model get chat from repo
        val result = chatViewModel.chats.getOrAwaitValue()

        // then result should be null (should be handled by UI)
        assertThat(result, `is`(emptyList()))
    }

    @Test
    fun sendMessage_whenSuccess_returnDefaultState() = runBlockingTest {

        // given that user write a message
        chatViewModel.newMessage.value = "message"

        // when viewModel send message when user click send
        coEvery { chatRepository.sendMessage(chatViewModel.newMessage.value) } returns ResponseWrapper.Success(
            SendResponse("message", "id", "createdAt")
        )
        chatViewModel.sendMessage()

        // then state should return to DefaultState
        val result = chatViewModel.viewState.getOrAwaitValue()

        assertThat(result is ViewState.DefaultState, `is`(true))
    }

    @Test
    fun sendMessage_whenError_returnDefaultState() = runBlockingTest {

        // when viewModel send a null message (no message is being set at chatViewModel.newMessage)
        coEvery { chatRepository.sendMessage(null) } returns ResponseWrapper.Error("message")
        chatViewModel.sendMessage()

        // then state should return to DefaultState
        val result = chatViewModel.viewState.getOrAwaitValue()

        assertThat(result is ViewState.DefaultState, `is`(true))
    }

    @Test
    fun sendMessage_whenError_showToastEvent_onlyOnce() {

        // when viewModel send a null message (no message is being set at chatViewModel.newMessage)
        coEvery { chatRepository.sendMessage(null) } returns ResponseWrapper.Error("message")
        chatViewModel.sendMessage()

        // then state should return to DefaultState
        // and trigger Toast error message
        val event = chatViewModel.showToast.getOrAwaitValue()
        assertThat(event.getContentIfNotHandled(), not(nullValue()))
        // calling getContentIfNotHandled again should return null
        assertThat(event.getContentIfNotHandled(), `is`(nullValue()))
    }
}