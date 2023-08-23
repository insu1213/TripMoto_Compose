package com.insu.tripmoto_compose.screen.trip_selection

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.common.composable.MenuTitleText
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.screen.splash.SplashViewModel
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun TripSelectionScreen(
    openAndPopUp: (String) -> Unit,
    viewModel: TripSelectionViewModel = hiltViewModel()
) {
    val trip = viewModel.trip.collectAsStateWithLifecycle(emptyList())
    var tripIsEmptyCheck by remember { mutableStateOf(false) }

    LaunchedEffect(trip.value) {
        tripIsEmptyCheck = trip.value.isEmpty()
    }
    //tripIsEmptyCheck = trip.value.isEmpty()

    if(tripIsEmptyCheck) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(AppText.trip_empty),
                color = colorResource(AppColor.gray_5),
                fontFamily = suitFamily,
                fontWeight = FontWeight.Normal,
            )
        }
    }



    Column(
    ) {
        Box(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, bottom = 8.dp),
        ) {
            MainTitleText(
                text = AppText.trip
            )
        }


        LazyColumn(
        ) {
            itemsIndexed(
                trip.value
            ) { _, tripItem ->
                TripItem(trip = tripItem) { tripId ->
                    viewModel.goMain(openAndPopUp, tripId)
                }
            }
        }
    }
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ExtendedFloatingActionButton(
            text = {
                Text(
                    text = "New Trip",
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(AppColor.white)
                )
            },
            icon = { Icon(Icons.Filled.Add, "", tint = colorResource(AppColor.white)) },
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .padding(bottom = 12.dp, end = 12.dp),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp,
                hoveredElevation = 2.dp,
                focusedElevation = 1.dp,
            ),
            containerColor = colorResource(AppColor.primary_800),
            onClick = {
                viewModel.goFore(openAndPopUp)
            }
        )
    }
}