package com.insu.tripmoto_compose.screen.main.menu.travel_management

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun TravelManageListItem(title: String, data: Trip, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when (title) {
                    "제목" -> {}
                    "위치" -> {}
                    "일정" -> {}
                    "멤버 관리" -> {
                        onClick("MemberScreen")
                    }
                    "여행 나가기" -> {
                        //onClick("")
                    }
                }
            },
        //horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(40.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = title,
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = colorResource(
                    if(title == "여행 나가기") {
                        AppColor.red
                    } else {
                        AppColor.black
                    }
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = when(title) {
                    "제목" -> data.title
                    "위치" -> data.region + " / " + data.city
                    "일정" -> data.startDate + " ~ " + data.endDate
                    "멤버 관리" -> ""
                    "여행 나가기" -> ""
                    else -> ""
                },
                fontFamily = suitFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = colorResource(AppColor.gray_7)
            )
//            Icon(
//                modifier = Modifier.padding(),
//                painter = painterResource(id = AppIcon.ic_right_arrow),
//                contentDescription = ""
//            )
        }
    }
}