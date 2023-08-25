package com.insu.tripmoto_compose.screen.travel_option

import android.graphics.drawable.ShapeDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.screen.fore.ForeViewModel
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun TravelOptionScreen(
    openAndPopUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TravelOptionViewModel = hiltViewModel()
) {
    var cardClickState by remember { mutableStateOf(false) }

    if (cardClickState) {
        TravelCodeDialog(onDismiss = {
            if (it) {
                viewModel.goToTripSelection(openAndPopUp)
            }
            cardClickState = false
        })
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Card(
                modifier = Modifier
                    .height(88.dp)
                    .width(232.dp)
                    .clickable {
                        cardClickState = true
                    },
                elevation = 4.dp,
                backgroundColor = colorResource(AppColor.primary_800),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.padding(6.dp))
                    Text(
                        text = "다른 여행에 참가",
                        fontSize = 16.sp,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white)
                    )
                    Spacer(Modifier.padding(10.dp))
                    Text(
                        text = "이미 생성된 여행에 참가합니다.",
                        fontSize = 12.sp,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.white)
                    )
                }
            }
            Spacer(Modifier.padding(14.dp))
            Card(
                modifier = Modifier
                    .height(88.dp)
                    .width(232.dp)
                    .clickable {
                        viewModel.onNewTripClick(openAndPopUp)
                    },
                elevation = 4.dp,
                backgroundColor = colorResource(AppColor.gray_5),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.padding(6.dp))
                    Text(
                        text = "새로운 여행 생성",
                        fontSize = 16.sp,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white)
                    )
                    Spacer(Modifier.padding(10.dp))
                    Text(
                        text = "새로운 여행일정을 생성합니다.",
                        fontSize = 12.sp,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.white)
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun TravelOptionScreenPreview() {
//    TravelOptionScreen()
//}