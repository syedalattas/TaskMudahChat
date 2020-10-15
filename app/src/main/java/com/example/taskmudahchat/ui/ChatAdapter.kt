package com.example.taskmudahchat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmudahchat.data.Chat
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
        view.tvMessage.text = chat.message
    }
}

class ChatIncomingViewHolder(private val view: ListChatIncomingBinding) :
    BaseViewHolder(view.root) {
    override fun bind(chat: Chat) {
        view.tvMessage.text = chat.message
    }
}