package com.example.taskmudahchat.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.repository.data.repository.FakeChatRepository
import com.example.taskmudahchat.util.CoroutinesTestRule
import com.example.taskmudahchat.util.getOrAwaitValue
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

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var fakeChatRepository: FakeChatRepository

    @Before
    fun createViewModel() {
        fakeChatRepository = FakeChatRepository()
        chatViewModel = ChatViewModel(fakeChatRepository)
    }

    @Test
    fun getChat_nonEmptyList_shouldNotBeNull() {

        // given that repo get chat successfully from data source
        fakeChatRepository.addChats(
            mutableListOf(
                Chat("timestamp", "direction", "message"),
                Chat("timestamp", "direction", "message"),
                Chat("timestamp", "direction", "message"),
            )
        )

        // when view model get chat from repo
        val result = fakeChatRepository.getChats().getOrAwaitValue()

        // then data should not be null
        assertThat(result, `is`(notNullValue()))
    }

    @Test
    fun getChat_empty_shouldReturnEmptyList() {

        // given that repo returns null
        fakeChatRepository.addChats(listOf())

        // when view model get chat from repo
        val result = chatViewModel.chats.getOrAwaitValue()

        // then result should be null (should be handled by UI)
        assertThat(result, `is`(emptyList()))
    }

    @Test
    fun sendMessage_whenSuccess_resetLoadingErrorMessage() = runBlockingTest {

        // given that user write a message
        chatViewModel.newMessage.value = "message"

        // viewModel send message when user click send
        chatViewModel.sendMessage()

        // then state should return to DefaultState
        val result = chatViewModel.viewState.getOrAwaitValue()
        val messageState = chatViewModel.newMessage.getOrAwaitValue()
        assertThat(result.isError, `is`(false))
        assertThat(result.isLoading, `is`(false))
        assertThat(messageState, `is`(nullValue()))
    }
}