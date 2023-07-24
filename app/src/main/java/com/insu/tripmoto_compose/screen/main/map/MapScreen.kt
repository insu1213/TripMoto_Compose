package com.insu.tripmoto_compose.screen.main.map

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.insu.tripmoto_compose.rememberAppState
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun MapScreen() {
    val activity = LocalContext.current as ComponentActivity
    var markerAddState by remember { mutableStateOf(false) }
    var googleMapClickState by remember { mutableStateOf(false) }
    BackHandler {
        activity.finish()
    }
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    if(googleMapClickState) {
        EditMarkerDialog {
            googleMapClickState = false
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            if(markerAddState) {
                googleMapClickState = true
            }
        }
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
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
}




