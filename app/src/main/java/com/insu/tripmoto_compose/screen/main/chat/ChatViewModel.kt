package com.insu.tripmoto_compose.screen.main.chat.inner

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.User
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val auth: AccountService
): MyViewModel(logService) {
    val currentUser = auth.currentUser
    var chatList = mutableStateOf(ChatList())
    val _chatListStorage = MutableStateFlow<List<ChatList>>(emptyList())
    val chatListStorage = storageService.chatList

    fun updateChatListStorage(newList: List<ChatList>) {
        _chatListStorage.value = newList
    }

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
            onSuccess()
        }
    }

    fun clearText() {
        launchCatching { chatList.value = chatList.value.copy(text = "") }
    }
}