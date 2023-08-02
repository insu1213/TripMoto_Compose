package com.insu.tripmoto_compose.screen.main.chat

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.model.service.ConfigurationService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
): MyViewModel(logService) {
    var chatList = mutableStateOf(ChatList())
    val chatListStorage = storageService.chatList

    fun onTextChange(newValue: String) {
        launchCatching { chatList.value = chatList.value.copy(text = newValue) }
    }

    fun onSendClick(onSuccess: () -> Unit) {
        if(chatList.value.text.isBlank()) {
            return
        }

        launchCatching {
            val editedChat = chatList.value

            if(editedChat.id.isBlank()) {
                storageService.saveChatList(editedChat)
            } else {
                storageService.saveChatList(editedChat)
            }
            chatList = mutableStateOf(ChatList())
            onSuccess()
        }
    }
}