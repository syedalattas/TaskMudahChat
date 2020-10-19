package com.example.taskmudahchat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.source.remote.ResponseWrapper
import com.example.taskmudahchat.util.Event
import kotlinx.coroutines.launch

class ChatViewModel @ViewModelInject constructor(private val chatRepository: ChatRepository) :
    ViewModel() {

    val chats: LiveData<List<Chat>> by lazy { chatRepository.getChats() }

    // Two-way dataBinding, exposing MutableLiveData
    val newMessage = MutableLiveData<String>()

    // expose UI state in a state class
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState.DefaultState())
    val viewState: LiveData<ViewState> = _viewState

    // observe once
    private val _showToast = MutableLiveData<Event<String>>()
    val showToast: LiveData<Event<String>> = _showToast

    fun sendMessage() {
        val message = newMessage.value
        _viewState.value = ViewState.LoadingState()
        viewModelScope.launch {

            // send message to api
            val result = chatRepository.sendMessage(message)

            // let UI return to default state, handle error if any
            _viewState.value = ViewState.DefaultState()
            // reset message
            newMessage.value = null
            if (result is ResponseWrapper.Error) {
                _showToast.value = Event(result.message!!)
            }
        }
    }
}

sealed class ViewState(
    val isLoading: Boolean? = false,
) {
    class DefaultState : ViewState()
    class LoadingState : ViewState(false)
}