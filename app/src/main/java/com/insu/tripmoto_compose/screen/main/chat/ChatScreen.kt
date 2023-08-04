package com.insu.tripmoto_compose.screen.main.chat

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.common.composable.MenuTitleText
import com.insu.tripmoto_compose.common.ext.spacer
import com.insu.tripmoto_compose.rememberAppState
import com.insu.tripmoto_compose.screen.main.wishlist.WishListViewModel
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as ComponentActivity
    val chatListStorage = viewModel.chatListStorage.collectAsStateWithLifecycle(emptyList())
    val chatList  by viewModel.chatList

    BackHandler {
        activity.finish()
    }
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 88.dp)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            MainTitleText(
                text = AppText.chat,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        LazyColumn() {
            itemsIndexed(
                items = chatListStorage.value,
                key = { _, item ->
                    item.id
                }
            ) { _, item ->
                ChatListItem(item)
            }
        }
    }


    Box(
        modifier
            .fillMaxSize()
            .padding(68.dp), contentAlignment = Alignment.BottomCenter) {
        Row() {
            TextField(
                value = chatList.text,
                onValueChange = viewModel::onTextChange,
                placeholder =  { Text("채팅을 입력하세요") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onSendClick {

                            }
                        }
                    ) {
                        Icon(painter = painterResource(AppIcon.ic_send), contentDescription = null,)
                    }
                }
            )

        }
    }
}
