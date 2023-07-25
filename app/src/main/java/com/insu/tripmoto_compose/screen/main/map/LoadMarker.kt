package com.insu.tripmoto_compose.screen.main.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.screen.main.map.detail.DetailMarkerDialog
import com.insu.tripmoto_compose.screen.main.map.edit.EditMarkerViewModel

@Composable
fun LoadMarker(viewModel: MapViewModel = hiltViewModel(), markerClick: (MapMarker) -> Unit) {
    val marker = viewModel.marker.collectAsStateWithLifecycle(emptyList())

    marker.value.forEach {
        val thisMarker = it
        val position = LatLng(it.lat, it.lng)
        Marker(
            state = MarkerState(position = position),
            title = it.title,
            snippet = it.description,
            onInfoWindowClick = {
                markerClick(thisMarker)
            }
        )
    }
}