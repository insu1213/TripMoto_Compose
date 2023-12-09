package com.insu.tripmoto_compose.screen.main.menu.travel_management

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor

enum class TravelManageList(val data: String) {
    Title("제목"),
    Location("위치"),
    Schedule("일정"),
    MemberManagement("멤버 관리"),
    Exit("여행 나가기")
}

@Composable
fun TravelManageListItem(title: TravelManageList, data: Trip, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when (title) {
                    TravelManageList.Title -> {

                    }

                    TravelManageList.Location -> {

                    }

                    TravelManageList.Schedule -> {

                    }

                    TravelManageList.MemberManagement -> {
                        onClick("MemberScreen")
                    }

                    TravelManageList.Exit -> {
                        onClick("")
                    }
//                    "제목" -> {}
//                    "위치" -> {}
//                    "일정" -> {}
//                    "멤버 관리" -> {
//                        onClick("MemberScreen")
//                    }
//                    "여행 나가기" -> {
//                        //onClick("")
//                    }
                    else -> {}
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
                text = title.data,
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = colorResource(
                    if(title == TravelManageList.Exit) {
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
                    TravelManageList.Title -> data.title
                    TravelManageList.Location -> data.region + " / " + data.city
                    TravelManageList.Schedule -> data.startDate + " ~ " + data.endDate
                    else -> ""
                },
                fontFamily = suitFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = colorResource(AppColor.gray_7)
            )
        }
    }
}