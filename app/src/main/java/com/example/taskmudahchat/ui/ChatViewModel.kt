package com.example.taskmudahchat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmudahchat.data.DataResource
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel @ViewModelInject constructor(private val chatRepository: ChatRepository) :
    ViewModel() {

    // Two-way dataBinding, exposing MutableLiveData
    val newMessage = MutableLiveData<String>()

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState.DefaultState())
    val viewState: LiveData<ViewState> = _viewState

    val chats: LiveData<List<Chat>> = chatRepository.getChats()

    fun sendMessage() {
        val message = newMessage.value
        _viewState.value = ViewState.LoadingState()
        viewModelScope.launch {
            val result = chatRepository.sendMessage(message!!)
            _viewState.value = ViewState.DefaultState()
            newMessage.value = null
            if (result is DataResource.Error) {
                _viewState.value = ViewState.ErrorState()
            }
        }
    }
}

sealed class ViewState(
    val isError: Boolean? = false, // to be used in the UI to show error message
    val isLoading: Boolean? = false,
) {
    class DefaultState : ViewState()
    class LoadingState : ViewState(false, true)
    class ErrorState: ViewState(true)
}