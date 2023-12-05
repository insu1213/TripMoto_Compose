package com.insu.tripmoto_compose.screen.main.map

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.ConfigurationService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.model.service.removeImageFromFirebaseStorage
import com.insu.tripmoto_compose.screen.MyViewModel
import com.insu.tripmoto_compose.screen.main.map.edit.EditMarkerViewModel
import com.insu.tripmoto_compose.screen.main.wishlist.WishListActionOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
): MyViewModel(logService) {

    val options = mutableStateOf<List<String>>(listOf())

    var markers: Flow<List<MapMarker>>

    init {
        markers = storageService.marker
    }

    fun loadMarkerOptions() {
        val hasEditOption = configurationService.isShowMarkerEditButtonConfig
        options.value = MarkerActionOption.getOptions(hasEditOption)
    }

    fun onMarkerActionClick(marker: MapMarker, action: String, editMarker: () -> Unit) {
        when (MarkerActionOption.getByTitle(action)) {
            MarkerActionOption.EditMarker -> editMarker()
            MarkerActionOption.DeleteMarker -> {
                onDeleteMarkerClick(marker)
            }
        }
    }

    fun onMarkerUpdated() {
        markers = storageService.marker
    }

    private fun onDeleteMarkerClick(marker: MapMarker) {
        launchCatching {
            storageService.deleteMarker(marker.id)
            //마커 삭제시 실행
            Log.d("a", "marker 삭제")
        }
    }

    fun newMarkerPosition(marker: MapMarker, position: LatLng) {
        launchCatching {
            storageService.updateMarker(
                marker.copy(lat = position.latitude, lng = position.longitude)
            )
        }
    }
}