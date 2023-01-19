package com.example.mychatbotapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mychatbotapp.ChatAdapter.Constant.BOT_MSG
import com.example.mychatbotapp.ChatAdapter.Constant.USER_MSG
import com.example.mychatbotapp.databinding.BotMsgItemViewBinding
import com.example.mychatbotapp.databinding.UserMsgItemViewBinding

class ChatAdapter(private val chatModelArrayList: ArrayList<ChatsModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private object Constant {
        const val USER_MSG = 0
        const val BOT_MSG = 1
    }

    override fun getItemCount(): Int = chatModelArrayList.size

    override fun getItemViewType(position: Int): Int {
        when(chatModelArrayList.get(position).sender){
            "user" -> {
                return USER_MSG
            }
            else -> {
                return BOT_MSG
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == USER_MSG) return UserViewHolder(
            UserMsgItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        else return BotViewHolder(
            BotMsgItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == USER_MSG) {
            (holder as UserViewHolder).bind(chatModelArrayList.get(position).message.orEmpty())
        }

        if (getItemViewType(position) == BOT_MSG) {
            (holder as BotViewHolder).bind(chatModelArrayList.get(position).message.orEmpty())
        }
    }

    class UserViewHolder(
        val binding: UserMsgItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userMsg: String) {
            binding.userMsg.text = userMsg
        }
    }

    class BotViewHolder(
        val binding: BotMsgItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(botMsg: String) {
            binding.botMsg.text = botMsg
        }
    }

}