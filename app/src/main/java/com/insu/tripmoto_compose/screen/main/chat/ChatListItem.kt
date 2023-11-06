package com.insu.tripmoto_compose.screen.main.chat

import android.content.ContentValues.TAG
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.screen.main.wishlist.ImageLoadViewModel
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun ChatListItem(
    chat: ChatList,
    userId: String, // 현재 로그인된 사용자 UID
    itemUserName: String // 아이템에 저장된 사용자 닉네임
) {
    val viewModel = ImageLoadViewModel()

    var itemIsChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val drawable = getDrawable(context, AppIcon.zoe)
    val bitmapDrawable = drawable as BitmapDrawable
    val bitmap = bitmapDrawable.bitmap

    val imageBitmapState: MutableState<ImageBitmap?> = remember { mutableStateOf(bitmap.asImageBitmap()) }

    // 이미 불러온 경우
    for(item in viewModel.imageDownloadState) {
        if(item.containsKey(userId)) {
            val value = item.getValue(userId)
            imageBitmapState.value = value
            itemIsChecked = true
            Log.d(TAG, "실행: 이전에 불러온 데이터 사용")
            break
        }
    }

    if(!itemIsChecked) {
//        Log.d(TAG, "실행: 새로 불러옴 $userId")
//        imageBitmapState.value = viewModel.getImageBitmap(chat.userId)?.value
    }

    if(chat.userId == userId) {
        MyMessageCard(chat, itemUserName, imageBitmapState)
    } else {
        MessageCard(chat, itemUserName, imageBitmapState)
    }
}

@Composable
fun MessageCard(msg: ChatList, nickName: String, imageBitmapState: State<ImageBitmap?>?) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        if(imageBitmapState!!.value != null) {
            imageBitmapState.value?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                )
            }
        } else {
            Image(
                painter = painterResource(R.drawable.zoe),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = nickName,
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
                    val uploadTimeDeci =
                        msg.uploadTime.split(" ")[1].split(":")[0] + ":" +
                            msg.uploadTime.split(" ")[1].split(":")[1]

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = uploadTimeDeci,
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
fun MyMessageCard(msg: ChatList, nickName: String, imageBitmapState: State<ImageBitmap?>?) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxWidth()) {
        Spacer(Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = nickName,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Box(
                    modifier = Modifier.height(IntrinsicSize.Max), // Box가 남은 공간을 차지하도록 weight 설정
                    contentAlignment = Alignment.BottomEnd
                ) {
                    val uploadTimeDeci =
                        msg.uploadTime.split(" ")[1].split(":")[0] + ":" +
                            msg.uploadTime.split(" ")[1].split(":")[1]

                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = uploadTimeDeci,
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

        if(imageBitmapState!!.value != null) {
            imageBitmapState.value?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                )
            }
        } else {
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
}
//@Preview
//@Composable
//fun MessageCardPreview() {
//    MessageCard(ChatList(text = "내 이름은 조이야~", userId = "조이, 여명의 성위", uploadTime = "12:25"), "조이, 여명의 성위", )
//}
//
//@Preview
//@Composable
//fun MyMessageCardPreview() {
//    MyMessageCard(ChatList(text = "내 이름은 조이야~", userId = "조이, 여명의 성위", uploadTime = "12:25"), "조이, 여명의 성위")
//}