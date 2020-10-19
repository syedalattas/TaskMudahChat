package com.example.taskmudahchat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmudahchat.data.source.remote.ResponseWrapper
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel @ViewModelInject constructor(private val chatRepository: ChatRepository) :
    ViewModel() {

    // Two-way dataBinding, exposing MutableLiveData
    val newMessage = MutableLiveData<String>()

    // we want to avoid the UI from effecting the value of the state so we will only
    // expose viewState, and the value of viewState will be controlled by _viewState
    // which is only available in this class
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState.DefaultState())
    val viewState: LiveData<ViewState> = _viewState

    val chats: LiveData<List<Chat>> = chatRepository.getChats()

    fun sendMessage() {
        val message = newMessage.value
        _viewState.value = ViewState.LoadingState()
        viewModelScope.launch {

            // send message to api
            val result = chatRepository.sendMessage(message!!)

            // let UI return to default state, handle error if any
            if (result is ResponseWrapper.Success) {
                _viewState.value = ViewState.DefaultState()
            } else {
                _viewState.value = ViewState.ErrorState()
            }
            // reset message no matter the state
            newMessage.value = null
        }
    }
}

sealed class ViewState(
    val isError: Boolean? = false, // to be used in the UI to show error message
    val isLoading: Boolean? = false,
) {
    class DefaultState : ViewState()
    class LoadingState : ViewState(false, true)
    class ErrorState : ViewState(true)
}