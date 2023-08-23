package com.insu.tripmoto_compose.screen.travel_management

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun TravelManageListItem(title: String, data: Trip) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when(title) {
                    "제목" -> {}
                    "위치" -> {}
                    "일정" -> {}
                    "멤버" -> {}
                    "경비" -> {}
                    "고급 설정" -> {}
                }
            }
    ) {
        Box(modifier = Modifier.padding(top = 14.dp, bottom = 12.dp)) {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterStart),
                text = title
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(end = 8.dp, top = 14.dp, bottom = 12.dp),
                    text = when(title) {
                        "제목" -> data.title
                        "위치" -> data.region + " / " + data.city
                        "일정" -> data.startDate + " ~ " + data.endDate
                        "멤버" -> ""
                        "경비" -> data.expenses
                        "고급 설정" -> ""
                        else -> ""
                    }
                )
                Icon(
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                    painter = painterResource(id = AppIcon.ic_right_arrow),
                    contentDescription = ""
                )
            }
        }
    }
}