package com.insu.tripmoto_compose.screen.main.chat.inner

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun ChatListItem(
    chat: ChatList,
    userId: String,
) {
    Log.d(TAG, "실행: chatUserId = ${chat.userId}")
    Log.d(TAG, "실행: userId = $userId")
    if(chat.userId == userId) {
        MyMessageCard(chat)
    } else {
        MessageCard(chat)
    }
}

@Composable
fun MessageCard(msg: ChatList) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.zoe),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = msg.userId,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Surface(
                    color = colorResource(AppColor.primary_800),
                    shape = MaterialTheme.shapes.medium,
                    elevation = 2.dp) {
                    Text(
                        text = msg.text,
                        modifier = Modifier.padding(all = 8.dp),
                        style = MaterialTheme.typography.body2,
                        color = colorResource(AppColor.white)
                    )
                }
                Box(
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = msg.uploadTime,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 8.sp,
                        color = colorResource(AppColor.gray_7),
                    )
                }
            }
        }
    }
}

@Composable
fun MyMessageCard(msg: ChatList) {
    Row(modifier = Modifier.padding(all = 8.dp)) {


        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = msg.userId,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Box(
                    modifier = Modifier.height(IntrinsicSize.Max), // Box가 남은 공간을 차지하도록 weight 설정
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = msg.uploadTime,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.End,
                        fontSize = 8.sp,
                        color = colorResource(AppColor.gray_7),
                    )
                }

                Surface(shape = MaterialTheme.shapes.medium, elevation = 2.dp) {
                    Text(
                        text = msg.text,
                        modifier = Modifier.padding(all = 8.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(R.drawable.zoe),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
    }
}
@Preview
@Composable
fun MessageCardPreview() {
    MessageCard(ChatList(text = "내 이름은 조이야~", userId = "조이, 여명의 성위", uploadTime = "12:25"))
}

@Preview
@Composable
fun MyMessageCardPreview() {
    MyMessageCard(ChatList(text = "내 이름은 조이야~", userId = "조이, 여명의 성위", uploadTime = "12:25"))
}