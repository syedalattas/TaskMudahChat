package com.example.taskmudahchat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.data.repository.ChatRepository
import com.example.taskmudahchat.data.source.remote.ResponseWrapper
import com.example.taskmudahchat.util.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// view state - observe data for state change like loading
// view effect - fire and forget like single live event - showing toast, navigate
// view event - user event
class ChatViewModel @ViewModelInject constructor(private val chatRepository: ChatRepository) :
    ViewModel() {

    private val mutableChats: MutableLiveData<List<Chat>> = MutableLiveData(arrayListOf())
    val chats: LiveData<List<Chat>> = mutableChats

    // Two-way dataBinding, exposing MutableLiveData
    val newMessage = MutableLiveData<String>()

    // listen for changes from newMessage, and reflect back to button in UI
    val enableSendButton: LiveData<Boolean> = Transformations.switchMap(newMessage) {
        MutableLiveData(it != null && it.isNotEmpty())
    }

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

    fun getChats() {
        viewModelScope.launch {
            chatRepository.getChats().collect {
                mutableChats.postValue(it)
            }
        }
    }
}

sealed class ViewState(
    val isLoading: Boolean? = false,
) {
    class DefaultState : ViewState()
    class LoadingState : ViewState(true)
}