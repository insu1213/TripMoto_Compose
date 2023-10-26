package com.insu.tripmoto_compose.screen.main.menu.travel_management

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.screen.trip_selection.TripSelectionViewModel
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun TravelManagementScreen(
    openAndPopUp: (String) -> Unit,
    viewModel: TravelManagementViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    val activity = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 12.dp, start = 12.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 4.dp)
                .clickable {
                    onBackPressed
                },
            painter = painterResource(id = AppIcon.ic_left_arrow),
            contentDescription = ""
        )
        MainTitleText(
            text = AppText.active_trip_management
        )
    }

    LazyColumn(modifier = Modifier.padding(top = 48.dp)) {
        itemsIndexed(
            listOf(
                TravelManageList.Title,
                TravelManageList.Location,
                TravelManageList.Schedule,
                TravelManageList.MemberManagement,
                TravelManageList.Exit,
            )
        ) { _, item ->
            TravelManageListItem(title = item, data = viewModel.trip.value) { route ->
                openAndPopUp(route)
            }
        }
    }
}