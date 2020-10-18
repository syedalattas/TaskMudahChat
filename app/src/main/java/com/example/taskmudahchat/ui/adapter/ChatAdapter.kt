package com.example.taskmudahchat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.taskmudahchat.data.model.Chat
import com.example.taskmudahchat.databinding.ListChatIncomingBinding
import com.example.taskmudahchat.databinding.ListChatOutgoingBinding


class ChatAdapter : ListAdapter<Chat, BaseViewHolder>(ChatDiffCallback()) {

    companion object {
        const val OUTGOING = 1
        const val INCOMING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            OUTGOING -> {
                val view = ListChatOutgoingBinding.inflate(layoutInflater, parent, false)
                ChatOutgoingViewHolder(view)
            }
            else -> {
                val view = ListChatIncomingBinding.inflate(layoutInflater, parent, false)
                ChatIncomingViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chat = getItem(position)
        return when (chat.direction) {
            "OUTGOING" -> OUTGOING
            else -> INCOMING
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}