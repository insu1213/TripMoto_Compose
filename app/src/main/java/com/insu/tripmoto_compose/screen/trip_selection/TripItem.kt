package com.insu.tripmoto_compose.screen.trip_selection


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun TripItem(
    trip: Trip,
    onClick: (tripId: String) -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick(trip.id) }
    ) {
        Image(
            modifier = Modifier
                .size(46.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            painter = painterResource(AppIcon.zoe),
            contentDescription = "",
        )
        Column(
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Text(
                text = trip.title,
                fontSize = 14.sp,
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = trip.region + " / " + trip.city,
                fontSize = 12.sp,
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(AppColor.gray_5)
            )
        }
    }
}

@Composable
@Preview
fun TripItemPreview() {
    TripItem(Trip(id = "", title = "2023일본여행", region = "일본", city = "도쿄")) {

    }
}