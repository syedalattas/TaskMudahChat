package com.example.taskmudahchat.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmudahchat.R
import com.example.taskmudahchat.databinding.ActivityChatBinding
import com.example.taskmudahchat.ui.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    // binding
    private lateinit var binding: ActivityChatBinding

    // data
    private val viewModel by viewModels<ChatViewModel>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        initBinding()
        initList()
        observeData()
        setupToast()
    }

    private fun initBinding() {
        binding.setLifecycleOwner { lifecycle }
        binding.chatViewModel = viewModel
    }

    private fun initList() {
        chatAdapter = ChatAdapter()
        binding.rlvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }
    }

    private fun observeData() {
        viewModel.chats.observe(this, {
            chatAdapter.submitList(it)
        })
    }

    private fun setupToast() {
        viewModel.showToast.observe(this, {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        })
    }
}