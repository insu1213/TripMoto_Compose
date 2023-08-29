package com.insu.tripmoto_compose.screen.main.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.drawToBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.screen.main.map.detail.DetailMarkerDialog
import com.insu.tripmoto_compose.screen.main.map.edit.EditMarkerViewModel
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun LoadMarker(viewModel: MapViewModel = hiltViewModel(), activity: ComponentActivity, markerClick: (MapMarker) -> Unit) {
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
            },
           //icon = BitmapDescriptorFactory.fromBitmap(createDrawableFromView(box))
            // icon 대신 composable을 넣을 수 있는지도 확인.

        )
    }
}


private fun createDrawableFromView(view: View, activity: ComponentActivity): Bitmap {
    val displayMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    view.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
    view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
    view.buildDrawingCache()
    val bitmap =
        Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)
    view.draw(canvas)

    return bitmap
}