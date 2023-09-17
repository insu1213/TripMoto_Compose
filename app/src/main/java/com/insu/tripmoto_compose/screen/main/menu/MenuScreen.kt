package com.insu.tripmoto_compose.screen.main.menu

import android.annotation.SuppressLint
import android.graphics.drawable.ShapeDrawable
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.common.network.ConnectionState
import com.insu.tripmoto_compose.common.network.connectivityState
import com.insu.tripmoto_compose.model.User
import com.insu.tripmoto_compose.suitFamily
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MenuScreen(
    openAndPopUp: (String) -> Unit,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as ComponentActivity
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val nickNameState by viewModel.nickName.collectAsState() // nickName을 collectAsState로 감지
    val emailState by viewModel.email.collectAsState() // email을 collectAsState로 감지



//    LaunchedEffect(viewModel.nickName, viewModel.email) {
//        nickname = viewModel.nickName
//        email = viewModel.email
//    }


    BackOnPressed()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            MainTitleText(
                modifier = Modifier.padding(bottom = 24.dp),
                text = R.string.menu,
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                color = colorResource(AppColor.gray_1)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = nickNameState,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = emailState,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.padding(6.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(60.dp)
            ) {
                itemsIndexed(
                    listOf(
                        Menu(paint = AppIcon.ic_panel, text = "여행 설정"),
                        Menu(paint = AppIcon.ic_air, text = "항공권"),
                        Menu(paint = AppIcon.ic_room, text = "숙박"),
                        Menu(paint = AppIcon.ic_tag, text = "지출 내역"),
                    )
                ) { _, item ->
                    MenuList(menu = item) {
                        when (it) {
                            "여행 설정" -> viewModel.onTripSettingClick(openAndPopUp)
                            "항공권" -> {}
                            "숙박" -> {}
                            "지출 내역" -> {}
                        }
                    }
                }
            }


//            Surface(
//                modifier = Modifier
//                    .padding(top = 12.dp)
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.2F)
//                    .clickable {
//                        viewModel.onTripSettingClick(openAndPopUp)
//                    },
//                color = colorResource(AppColor.white),
//                shape = RoundedCornerShape(12.dp),
//                elevation = 4.dp,
//                border = BorderStroke(3.dp, colorResource(AppColor.primary_800))
//            ) {
//                Text(
//                    modifier = Modifier
//                        .padding(all = 8.dp)
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .wrapContentHeight(),
//                    text = stringResource(AppText.active_trip_management),
//                    style = MaterialTheme.typography.body2,
//                    color = colorResource(R.color.black),
//                    textAlign = TextAlign.Center,
//                    fontSize = 18.sp,
//                    fontFamily = suitFamily,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxHeight(0.14F)
//                    .padding(top = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Surface(
//                    modifier = Modifier
//                        .padding(end = 8.dp)
//                        .fillMaxWidth(0.5F)
//                        .fillMaxHeight(),
//                    color = colorResource(AppColor.white),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = 4.dp,
//                    border = BorderStroke(3.dp, colorResource(AppColor.gray_4))
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .padding(all = 8.dp)
//                            .fillMaxWidth()
//                            .fillMaxHeight()
//                            .wrapContentHeight(),
//                        text = stringResource(AppText.past_trip_record),
//                        style = MaterialTheme.typography.body2,
//                        color = colorResource(R.color.black),
//                        textAlign = TextAlign.Center,
//                        fontSize = 14.sp,
//                        fontFamily = suitFamily,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                }
//                Surface(
//                    modifier = Modifier
//                        .padding(start = 8.dp)
//                        .fillMaxHeight()
//                        .clickable {
//                            viewModel.onOtherTripClick(openAndPopUp)
//                        },
//                    color = colorResource(AppColor.white),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = 4.dp,
//                    border = BorderStroke(3.dp, colorResource(AppColor.gray_4))
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .padding(all = 8.dp)
//                            .fillMaxWidth()
//                            .fillMaxHeight()
//                            .wrapContentHeight(),
//                        text = stringResource(AppText.participate_other_travel),
//                        style = MaterialTheme.typography.body2,
//                        color = colorResource(R.color.black),
//                        textAlign = TextAlign.Center,
//                        fontSize = 14.sp,
//                        fontFamily = suitFamily,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                }
//            }

//// 2023.09.04
//            Text(
//                modifier = Modifier
//                    .clickable {
//                        //viewModel.onTriggerNotification(activity)
//                    },
//                text = "Notification",
//                fontSize = 16.sp,
//                color = colorResource(AppColor.primary_800)
//            )
        }
    }
}
