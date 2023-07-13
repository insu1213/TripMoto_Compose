package com.insu.tripmoto_compose.screen.fore.travel_members

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.screen.fore.ForeViewModel

@Composable
fun TravelMembersScreen(
    openAndPopUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
}