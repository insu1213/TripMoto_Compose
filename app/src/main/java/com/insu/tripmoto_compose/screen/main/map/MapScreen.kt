package com.insu.tripmoto_compose.screen.main.map

import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.common.network.ConnectionState
import com.insu.tripmoto_compose.common.network.connectivityState
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.screen.main.map.detail.DetailMarkerDialog
import com.insu.tripmoto_compose.screen.main.map.edit.EditMarkerDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.drawable as AppIcon

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
    var mapLoaded by remember { mutableStateOf(false) }

    val options by viewModel.options

    val activity = LocalContext.current as ComponentActivity

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

    if(!mapLoaded) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(R.color.primary_800),
            )
        }
    }


    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            if(markerAddState) {
                clickPosition = it
                googleMapClickState = true
            }
        },
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
        properties = MapProperties(isMyLocationEnabled = true),
        onMapLoaded = { mapLoaded = true }
    ) {
        LoadMarker(activity = activity) {
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

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun EditMarker(clickPosition: LatLng, markerId: String = "-1", onFinish: () -> Unit) {
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    if(isConnected) {
        EditMarkerDialog(
            position = clickPosition,
            markerId = markerId,
            onDismiss = {
                onFinish()
            })
    } else {
        SnackbarManager.showMessage(R.string.network_error)
    }
}




