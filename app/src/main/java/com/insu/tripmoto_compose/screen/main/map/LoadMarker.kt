package com.insu.tripmoto_compose.screen.main.map

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.insu.tripmoto_compose.model.MapMarker
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.DragState
import com.google.maps.android.compose.InputHandler
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberMarkerState
import com.insu.tripmoto_compose.screen.main.map.edit.toColor
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

//@Composable
//fun LoadMarker(viewModel: MapViewModel = hiltViewModel(), activity: ComponentActivity, markerClick: (MapMarker) -> Unit) {
//    val marker = viewModel.marker.collectAsStateWithLifecycle(emptyList())
//
//    var i = 0
//
//    marker.value.sortedBy { it.uploadTime }.forEach { marker ->
//        i += 1
//        val thisMarker = marker
//        val position = LatLng(marker.lat, marker.lng)
//
//        val markerState = rememberMarkerState(
//            position = position
//        )
//
//        val markerClickEvent: (Marker) -> Boolean = { _ ->
//            Log.d(TAG, "${marker.title} was clicked")
//            markerClick(thisMarker)
//            true
//        }
//
//        //https://issuetracker.google.com/issues/220892485
//        MarkerComposable(
//            title = marker.title,
//            keys = arrayOf("singapore4"),
//            state = markerState,
//            onClick = markerClickEvent,
//            alpha = 0.7f,
//            draggable = true,
//        ) {
//            Box(
//                modifier = Modifier
//                    .width(36.dp)
//                    .height(36.dp)
//                    .clip(CircleShape)
//                    .background(marker.color.toColor(Color.White))
//                    .border(width = 2.dp, colorResource(AppColor.white), shape = CircleShape),
//                contentAlignment = Alignment.Center,
//            ) {
//                Text(
//                    text = "$i",
//                    textAlign = TextAlign.Center,
//                    color = colorResource(AppColor.white),
//                    fontFamily = suitFamily,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 12.sp
//                )
//            }
//        }
//        LaunchedEffect(markerState.position) {
//            Log.d(TAG, "마커이동")
//            viewModel.newMarkerPosition(marker, markerState.position)
//        }
//    }
//}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadMarker(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: MapViewModel = hiltViewModel(),
    activity: ComponentActivity,
    markerFlow: State<List<MapMarker>>,
    markerClick: (MapMarker) -> Unit
) {
    val dragStart = remember { mutableStateOf(false) }

//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_RESUME) {
//                markerFlow = mutableStateOf(emptyList())
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

    var i = 0

    markerFlow.value.sortedBy { it.uploadTime }.forEach { marker ->
        i += 1

        //Log.d(TAG, "markerFlow: $marker")

         val position = LatLng(marker.lat, marker.lng)
        Log.d(TAG, "markerFlowPos: $position")

        val markerState = rememberMarkerState(
            position = position
        )

        //markerState.position = position

        Log.d(TAG, "markerFlowState: ${markerState.position}")

        //var touchState by remember { mutableStateOf(false) }

        val markerClickEvent: (Marker) -> Boolean = { _ ->
            Log.d(TAG, "${marker.title} was clicked")
            markerClick(marker)
            true
        }



        MarkerComposable(
            title = marker.title,
            keys = arrayOf("marker"),
            state = markerState,
            onClick = markerClickEvent,
            alpha = 0.7f,
            draggable = true,
        ) {
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clip(CircleShape)
                    .background(marker.color.toColor(Color.White))
                    .border(width = 2.dp, colorResource(AppColor.white), shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "$i",
                    textAlign = TextAlign.Center,
                    color = colorResource(AppColor.white),
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }

        if(markerState.dragState == DragState.START) {
            dragStart.value = true
        }
        if(markerState.dragState == DragState.END && dragStart.value) {
            //position = markerState.position
            viewModel.newMarkerPosition(marker, markerState.position)
            Log.d(TAG, "마커 이동 완료됨: ${markerState.position}")
            dragStart.value = false
        }
    }
}


@Deprecated("This function is Deprecated. Use MarkerComposable.")
private fun bitMapFromVector(activity: ComponentActivity, vectorResID:Int): BitmapDescriptor {
    val vectorDrawable= ContextCompat.getDrawable(activity,vectorResID)
    vectorDrawable!!.setBounds(0,0, 120, 120)
    val bitmap=Bitmap.createBitmap(120,120,Bitmap.Config.ARGB_8888)
    val canvas=Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
@Deprecated("This function is Deprecated. Use MarkerComposable.")
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