package com.example.taskmudahchat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.data.DataResource
import com.example.taskmudahchat.data.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel @ViewModelInject constructor(private val chatRepository: ChatRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    val newMessage = MutableLiveData<String>()

    val chats: LiveData<List<Chat>> = chatRepository.getChats()

    fun sendMessage() {
        _isLoading.value = true
        val message = newMessage.value
        viewModelScope.launch {
            val result = chatRepository.sendMessage(message!!)
            resetState()
            if (result.status == DataResource.Status.ERROR) {
                _isError.value = true
            }
        }
    }

    private fun resetState() {
        _isError.value = false
        _isLoading.value = false
        newMessage.value = null
    }
}