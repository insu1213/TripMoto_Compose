package com.insu.tripmoto_compose.screen.trip_selection

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
        storageService.currentTripId.value = tripId
        openAndPopUp("MainScreen")
    }
}