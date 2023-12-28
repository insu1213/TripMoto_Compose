package com.insu.tripmoto_compose.screen.main.menu.travel_management

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.common.composable.BackPress
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.screen.trip_selection.TripSelectionViewModel
import kotlin.coroutines.coroutineContext
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun TravelManagementScreen(
    openAndPopUp: (String) -> Unit,
    viewModel: TravelManagementViewModel = hiltViewModel()
) {
    var exitState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    BackHandler() {
        viewModel.popUpBackStack(openAndPopUp)
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
                    viewModel.popUpBackStack(openAndPopUp)
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
                if(route != "Exit") {
                    openAndPopUp(route)
                } else {
                    //만약 그룹장이라면 나가기 불가능.
                    if(viewModel.trip.value.administrator == viewModel.accountService.currentUserId) {
                        SnackbarManager.showMessage(AppText.do_not_exitTrip)
                    } else {
                        exitState = true
                    }
                }
            }
        }
    }

    if(exitState) {
        TravelExitDialog() {
            exitState = false
            if(it == 1) {
                viewModel.exitTrip(openAndPopUp)
            }
        }
    }
}