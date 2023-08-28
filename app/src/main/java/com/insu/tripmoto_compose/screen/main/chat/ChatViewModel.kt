package com.insu.tripmoto_compose.screen.main.chat.inner

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.model.ChatList
import kotlin.collections.remove
import com.insu.tripmoto_compose.model.User
import com.insu.tripmoto_compose.model.UserInfo
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.NotificationService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import com.insu.tripmoto_compose.screen.main.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

@HiltViewModel
class ChatViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val auth: AccountService
): MyViewModel(logService) {
    val currentUser = auth.currentUser
    var chatList = mutableStateOf(ChatList())
    private val _chatListStorage = MutableStateFlow<List<ChatList>>(emptyList())
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
        val uid = auth.currentUserId
        uidToNickName(chatList.value.text, uid) { userInfo, text ->
            chatList.value = chatList.value.copy(nickName = userInfo.nickName, text = text)
            launchCatching {
                val tripId = storageService.getCurrentTripId()
                val trip = storageService.getTrip(tripId)
                val newTripMembers = trip!!.member.filter { it != auth.currentUserId }
                chatList.value = chatList.value.copy(readMembers = newTripMembers)

                withContext(Dispatchers.IO) {
                    val editedChat = chatList.value
                    storageService.saveChatList(editedChat)
                    chatList.value = ChatList()
                }
            }
        }
//            if(editedChat.id.isBlank()) {
//                storageService.saveChatList(editedChat)
//            } else {
//                storageService.saveChatList(editedChat)
//            }


//        Log.d(TAG, "chatList.value: ${chatList.value}")
//        onTextChange("")
        onSuccess()
    }

    fun chatAlert(activity: ComponentActivity, chatList: ChatList) {
        val uid = auth.currentUserId
        if(chatList.readMembers.contains(uid)) {
            val notificationService = NotificationService(activity.applicationContext)
            notificationService.showBasicNotification("TripMotoChat", "${chatList.nickName}: ${chatList.text}")

            val newMembers = chatList.readMembers.filter { it != auth.currentUserId }
            val newChatList = chatList.copy(readMembers = newMembers)
            launchCatching {
                storageService.updateChatList(newChatList)
            }
        }
    }

    private fun uidToNickName(text:String = "", uid: String, callback: (UserInfo, String) -> Unit) {
        viewModelScope.launch {
            val userInfo = storageService.getUserInfo(uid)

            withContext(Dispatchers.Main) {
                callback(userInfo?: UserInfo(), text)
            }
        }
    }


    fun clearText() {
        //chatList.value = chatList.value.copy(text = "")
    }
}