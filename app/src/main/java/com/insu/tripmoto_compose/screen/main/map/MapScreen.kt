package com.insu.tripmoto_compose.screen.main.map

import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.screen.main.map.detail.DetailMarkerDialog
import com.insu.tripmoto_compose.screen.main.map.edit.EditMarkerDialog
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    BackOnPressed()

    var markerAddState by remember { mutableStateOf(false) }
    var googleMapClickState by remember { mutableStateOf(false) }
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    var clickPosition by remember { mutableStateOf(LatLng(0.0, 0.0)) };
    var markerClickState by remember { mutableStateOf(false) }
    var markerClick by remember { mutableStateOf(MapMarker()) }

    var editState by remember { mutableStateOf(false) }

    val options by viewModel.options

    if(googleMapClickState) {
        EditMarker(clickPosition) {
            googleMapClickState = false
            markerAddState = false
        }
    }

    if(editState) {
        EditMarker(LatLng(markerClick.lat, markerClick.lng), markerClick.id) {
            editState = false
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            if(markerAddState) {
                clickPosition = it
                googleMapClickState = true
            }
        }
    ) {
        LoadMarker() {
            markerClick = it
            markerClickState = true
        }

        if(markerClickState) {
            DetailMarkerDialog(
                marker = markerClick,
                options = options,
                onActionClick = { action ->
                    viewModel.onMarkerActionClick(
                        markerClick,
                        action
                    ) {
                        editState = true
                    }
                }
            ) {
                markerClickState = false
            }
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Icon(
        modifier = Modifier
            .offset(x = screenWidth - 46.dp, y = screenHeight - 140.dp)
            .size(36.dp)
            .clickable {
                markerAddState = !markerAddState
            },
        painter = painterResource(AppIcon.ic_add_marker),
        tint =
            if(!markerAddState) colorResource(AppColor.gray_4)
            else colorResource(AppColor.primary_800),
        contentDescription = "addMarker",
    )
    LaunchedEffect(viewModel) { viewModel.loadMarkerOptions() }
}

@Composable
fun EditMarker(clickPosition: LatLng, markerId: String = "-1", onFinish: () -> Unit) {
    EditMarkerDialog(
        position = clickPosition,
        markerId = markerId,
        onDismiss = {
            onFinish()
        })
}




