package com.insu.tripmoto_compose.screen.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.screen.trip_selection.TripItem
import com.insu.tripmoto_compose.screen.trip_selection.TripSelectionViewModel

@Composable
fun SettingsScreen(
    openAndPopUp: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier.padding(top = 12.dp)
    ) {
        itemsIndexed(
            listOf(
                "계정",
                "알림",
                "언어",
                "공지사항",
                "로그아웃",
            )
        ) { _, item ->
            SettingsItem(item) { data ->
                when(data) {
                    "계정" -> {

                    }
                    "알림" -> {

                    }
                    "언어" -> {

                    }
                    "공지사항" -> {

                    }
                    "로그아웃" -> {
                        viewModel.signOut {
                            openAndPopUp("LoginScreen")

                        }
                    }
                }
            }
        }
    }
}