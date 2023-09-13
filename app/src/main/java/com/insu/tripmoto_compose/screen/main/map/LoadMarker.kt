package com.insu.tripmoto_compose.screen.main.map

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.insu.tripmoto_compose.model.MapMarker
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.MarkerComposable
import com.insu.tripmoto_compose.screen.main.map.edit.toColor
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.drawable as AppIcon
import com.insu.tripmoto_compose.R.color as AppColor

@Composable
fun LoadMarker(viewModel: MapViewModel = hiltViewModel(), activity: ComponentActivity, markerClick: (MapMarker) -> Unit) {
    val marker = viewModel.marker.collectAsStateWithLifecycle(emptyList())

    var i = 0

    marker.value.sortedByDescending { it.uploadTime }.forEach {
        i += 1
        val thisMarker = it
        val position = LatLng(it.lat, it.lng)

//        Marker(
//            state = MarkerState(position = position),
//            title = it.title,
//            snippet = it.description,
//            onInfoWindowClick = {
//                markerClick(thisMarker)
//            },
//            icon = bitMapFromVector(activity, AppIcon.zoe)
//        )

        val markerClickEvent: (Marker) -> Boolean = { marker ->
            Log.d(TAG, "${marker.title} was clicked")
            markerClick(thisMarker)
            true
        }
        //https://issuetracker.google.com/issues/220892485
        MarkerComposable(
            title = it.title,
            keys = arrayOf("singapore4"),
            state = MarkerState(position = position),
            onClick = markerClickEvent,
            draggable = true
        ) {
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clip(CircleShape)
                    .background(it.color.toColor(Color.White))
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
    }
}



//private fun bitMapFromVector(activity: ComponentActivity, vectorResID:Int): BitmapDescriptor {
//    val vectorDrawable= ContextCompat.getDrawable(activity,vectorResID)
//    vectorDrawable!!.setBounds(0,0, 120, 120)
//    val bitmap=Bitmap.createBitmap(120,120,Bitmap.Config.ARGB_8888)
//    val canvas=Canvas(bitmap)
//    vectorDrawable.draw(canvas)
//    return BitmapDescriptorFactory.fromBitmap(bitmap)
//}


//private fun createDrawableFromView(view: View, activity: ComponentActivity): Bitmap {
//    val displayMetrics = DisplayMetrics()
//    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
//    view.layoutParams = ViewGroup.LayoutParams(
//        ViewGroup.LayoutParams.WRAP_CONTENT,
//        ViewGroup.LayoutParams.WRAP_CONTENT
//    )
//    view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
//    view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
//    view.buildDrawingCache()
//    val bitmap =
//        Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
//
//    val canvas = Canvas(bitmap)
//    view.draw(canvas)
//
//    return bitmap
//}