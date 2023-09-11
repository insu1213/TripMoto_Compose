package com.insu.tripmoto_compose.screen.main.chat.inner

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.common.composable.CustomTextField
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.model.User
import com.insu.tripmoto_compose.screen.main.chat.ChatListItem
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {

    val chatListStorage = viewModel.chatListStorage.collectAsStateWithLifecycle(emptyList())
    val auth = viewModel.currentUser.collectAsStateWithLifecycle(User())

    var chatSendState by remember { mutableStateOf(true) }

    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis() + 1500) }
    var lastClickTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var clickableState by remember { mutableStateOf(false) }

    val activity = LocalContext.current as ComponentActivity

    val chatList by viewModel.chatList

    BackOnPressed()

    LaunchedEffect(currentTime) {
        currentTime = System.currentTimeMillis()
        if(currentTime - lastClickTime > 1500) {
            clickableState = true
        }
    }


    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 120.dp)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            MainTitleText(
                text = AppText.chat,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(top = 8.dp, start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(
                items = chatListStorage.value.sortedBy { it.uploadTime }, // 정렬
            ) { _, item ->
                //viewModel.chatAlert(activity, item)
                ChatListItem(item, auth.value.id, item.nickName)
            }
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .padding(68.dp), contentAlignment = Alignment.BottomCenter
    ) {
        Row() {
            TextField(
                value = chatList.text,
                onValueChange = viewModel::onTextChange,
                placeholder = { Text("채팅을 입력하세요") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if(clickableState) {
                                Log.d(TAG, "실행됨")
                                clickableState = false
                                lastClickTime = currentTime
                                viewModel.onSendClick {
                                    viewModel.clearText()
                                }
                            }
                        }
                    ) {
                        Icon(painter = painterResource(AppIcon.ic_send), contentDescription = null)
                    }
                }
            )
        }
    }
}

