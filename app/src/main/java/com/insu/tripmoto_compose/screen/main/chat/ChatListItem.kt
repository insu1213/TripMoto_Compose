package com.insu.tripmoto_compose.screen.main.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun ChatListItem(
    chat: ChatList,
) {
    MessageCard(Message(chat.userId, chat.text, chat.uploadTime))
}

data class Message(val author: String, val body: String, val uploadTime: String)
@Composable
fun MessageCard(msg: Message) {
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
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium, elevation = 2.dp) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 8.dp),
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Box(modifier = Modifier.padding(top = 50.dp), contentAlignment = Alignment.BottomStart) {
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

@Preview
@Composable
fun MessageCardPreview() {
    MessageCard(Message("조이, 여명의 성위", "내 이름은 조이야~", "12:25"))
}