package com.insu.tripmoto_compose.screen.member

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun MemberList(uid: String, nickName: String, email: String, permission: String, onClick: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(uid)
            }
        ) {
            Row() {
                Text(modifier = Modifier
                    .padding(start = 12.dp, end = 8.dp),
                    text = nickName,
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = permission,
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(AppColor.gray_7),
                    fontSize = 14.sp
                )
            }

            Text(modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 12.dp),
                text = email,
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = colorResource(AppColor.gray_7)
            )
        }
    }
}

//@Composable
//@Preview
//fun MemberListPreView() {
//    MemberList("insu1213", "insu12345_@naver.com", "그룹장")
//}