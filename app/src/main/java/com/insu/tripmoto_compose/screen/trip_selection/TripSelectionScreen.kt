package com.insu.tripmoto_compose.screen.trip_selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insu.tripmoto_compose.common.composable.MenuTitleText
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.screen.splash.SplashViewModel
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun TripSelectionScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TripSelectionViewModel = hiltViewModel()
) {
    val trip = viewModel.trip.collectAsStateWithLifecycle(emptyList())

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        MenuTitleText(text = AppText.trip)

        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                trip.value
            ) { index, tripItem ->
                TripItem(trip = tripItem)
            }
        }
    }
}