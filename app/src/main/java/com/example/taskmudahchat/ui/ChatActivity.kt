package com.example.taskmudahchat.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmudahchat.R
import com.example.taskmudahchat.databinding.ActivityChatBinding
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
        setContentView(R.layout.activity_chat)

        initBinding()
        initList()
        observeData()
    }

    private fun initBinding() {
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
}