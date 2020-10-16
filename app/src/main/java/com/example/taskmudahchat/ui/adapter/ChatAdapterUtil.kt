package com.example.taskmudahchat.ui.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmudahchat.data.Chat
import com.example.taskmudahchat.databinding.ListChatIncomingBinding
import com.example.taskmudahchat.databinding.ListChatOutgoingBinding

class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean =
        oldItem.timestamp == newItem.timestamp

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean =
        oldItem.timestamp == newItem.timestamp
}

abstract class BaseViewHolder internal constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(chat: Chat)

}

class ChatOutgoingViewHolder(private val view: ListChatOutgoingBinding) :
    BaseViewHolder(view.root) {
    override fun bind(chat: Chat) {
        view.chat = chat
    }
}

class ChatIncomingViewHolder(private val view: ListChatIncomingBinding) :
    BaseViewHolder(view.root) {
    override fun bind(chat: Chat) {
        view.chat = chat
    }
}