package com.insu.tripmoto_compose.screen.main.map

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import com.insu.tripmoto_compose.common.ext.idFromParameter
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
): MyViewModel(logService) {
    val marker = mutableStateOf(MapMarker())

    fun initialize(markerId: String) {
        launchCatching {
            if(markerId != "-1") {
                marker.value = storageService.getMarker(markerId.idFromParameter()) ?: MapMarker()
            }
        }
    }

    fun onTitleChange(newValue: String) {
        marker.value = marker.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        marker.value = marker.value.copy(description = newValue)
    }

    fun onPositionChange(newValue: LatLng) {
        marker.value = marker.value.copy(lat = newValue.latitude)
        marker.value = marker.value.copy(lng = newValue.longitude)
    }

    fun onDoneClick(onDismiss: () -> Unit) {
        if(marker.value.title.isBlank()) {
            Log.d(TAG, "Error: Title cannot empty")
            return
        }
        if(marker.value.description.isBlank()) {
            Log.d(TAG, "Error: Description cannot empty")
            return
        }

        launchCatching {
            val editedMarker = marker.value
            var markerId: String? = null

            if(editedMarker.id.isBlank()) {
                markerId = storageService.saveMarker(editedMarker)
            } else {
                storageService.updateMarker(editedMarker)
            }
            onDismiss()
        }
    }
}