package com.insu.tripmoto_compose.screen.main.chat.inner

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.insu.tripmoto_compose.suitFamily
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.color as AppColor

@OptIn(ExperimentalFoundationApi::class)
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
        if (currentTime - lastClickTime > 1500) {
            clickableState = true
        }
    }


    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 128.dp)
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

            val groupItems = chatListStorage.value.sortedBy { it.uploadTime }.groupBy {
                it.uploadTime.substringBefore(" ")
            }
            groupItems.forEach { (header, models) ->
                stickyHeader {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = header.split("-")[0] + "년 " +
                                header.split("-")[1] + "월 " +
                                header.split("-")[2] + "일",
                            color = Color.White,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(Color.Gray)
                                .width(200.dp)
                                .align(Alignment.Center)
                                .padding(1.dp),
                            textAlign = TextAlign.Center,
                            fontFamily = suitFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
                itemsIndexed(
                    items = models
                ) { _, item ->
                    //viewModel.chatAlert(activity, item)
                    ChatListItem(item, auth.value.id, item.nickName)
                }
            }



        }
    }

    Box(
        modifier
            .fillMaxSize()
            .padding(68.dp), contentAlignment = Alignment.BottomCenter
    ) {
        Row() {
//            TextField(
//                value = chatList.text,
//                onValueChange = viewModel::onTextChange,
//                placeholder = { Text("채팅을 입력하세요") },
//                trailingIcon = {
//                    IconButton(
//                        onClick = {
//                            if(clickableState) {
//                                Log.d(TAG, "실행됨")
//                                clickableState = false
//                                lastClickTime = currentTime
//                                viewModel.onSendClick {
//                                    viewModel.clearText()
//                                }
//                            }
//                        }
//                    ) {
//                        Icon(painter = painterResource(AppIcon.ic_send), contentDescription = null)
//                    }
//                }
//            )
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = colorResource(AppColor.gray_1)
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        BasicTextField(
                            value = chatList.text,
                            onValueChange = viewModel::onTextChange,
                            decorationBox = { innerTextField ->
                                Box(modifier = modifier) {
                                    if (chatList.text.isEmpty()) {
                                        Text(
                                            text = "채팅을 입력하세요.",
                                            color = colorResource(AppColor.gray_8),
                                            fontFamily = suitFamily,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.CenterStart),

                            maxLines = 1
                        )
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 12.dp)
                                .clickable {
                                    if(clickableState) {
                                        clickableState = false
                                        lastClickTime = currentTime
                                        viewModel.onSendClick {
                                            viewModel.clearText()
                                        }
                                    }
                                },
                            painter = painterResource(AppIcon.ic_send),
                            contentDescription = "",
                        )
                    }
                }
            }
        }
    }
}

