package com.insu.tripmoto_compose.screen.main.menu

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.rememberAppState
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun MenuScreen() {
    val activity = LocalContext.current as ComponentActivity
    BackHandler {
        activity.finish()
    }


    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        MainTitleText(
            text = R.string.menu,
        )
        Surface(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.2F),
            color = colorResource(AppColor.white),
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp,
            border = BorderStroke(3.dp, colorResource(AppColor.primary_800))
        ) {
            Text(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(),
                text = stringResource(AppText.active_trip_management),
                style = MaterialTheme.typography.body2,
                color = colorResource(R.color.black),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontFamily = suitFamily,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight(0.14F)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth(0.5F)
                    .fillMaxHeight(),
                color = colorResource(AppColor.white),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                border = BorderStroke(3.dp, colorResource(AppColor.gray_4))
            ) {
                Text(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(),
                    text = stringResource(AppText.past_trip_record),
                    style = MaterialTheme.typography.body2,
                    color = colorResource(R.color.black),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Surface(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxHeight(),
                color = colorResource(AppColor.white),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                border = BorderStroke(3.dp, colorResource(AppColor.gray_4))
            ) {
                Text(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(),
                    text = stringResource(AppText.participate_other_travel),
                    style = MaterialTheme.typography.body2,
                    color = colorResource(R.color.black),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}