package com.insu.tripmoto_compose.screen.main.menu.travel_management

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.screen.trip_selection.TripSelectionViewModel
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun TravelManagementScreen(
    openAndPopUp: (String) -> Unit,
    viewModel: TravelManagementViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 12.dp, start = 12.dp)
    ) {
        MainTitleText(
            text = AppText.active_trip_management
        )
    }

    LazyColumn(modifier = Modifier.padding(top = 48.dp)) {
        itemsIndexed(
            listOf(
                "제목",
                "위치",
                "일정",
                "멤버 관리",
            )
        ) { _, item ->
            TravelManageListItem(title = item, data = viewModel.trip.value) { route ->
                openAndPopUp(route)
            }
        }
    }
}