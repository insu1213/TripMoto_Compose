package com.insu.tripmoto_compose.screen.trip_selection

import android.content.ContentValues.TAG
import android.util.Log
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripSelectionViewModel @Inject constructor(
        val storageService: StorageService,
        logService: LogService
): MyViewModel(logService) {
    val trip = storageService.trip

    fun goMain(openAndPopUp: (String) -> Unit, tripId: String) {
        Log.d(TAG, "tripId: $tripId")
        launchCatching {
            storageService.updateCurrentTripId(tripId)
            Log.d(TAG, "currentTrip: ${storageService.getCurrentTripId()}")
            openAndPopUp("MainScreen")
        }
    }

    fun goFore(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TravelOptionScreen")
    }
}