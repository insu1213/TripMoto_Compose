package com.insu.tripmoto_compose.screen.main.chat

import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
): MyViewModel(logService) {
    val chatList = mutableStateOf(ChatList())


}