package com.insu.tripmoto_compose.settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.screen.trip_selection.TripItem
import com.insu.tripmoto_compose.screen.trip_selection.TripSelectionViewModel

@Composable
fun SettingsScreen(
    openAndPopUp: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {


    LazyColumn(
    ) {
        itemsIndexed(
            listOf(
                "알림",
                "로그아웃"
            )
        ) { _, item ->
            SettingsItem(item) { data ->
                when(data) {
                    "알림" -> {

                    }
                    "로그아웃" -> {
                        viewModel.signOut {
                            openAndPopUp("")
                        }
                    }
                }
            }
        }
    }
}