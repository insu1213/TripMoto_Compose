package com.insu.tripmoto_compose.screen.main.menu.travel_management

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TravelManagementViewModel @Inject constructor(
    logService: LogService,
    val storageService: StorageService
): MyViewModel(logService) {
    private val tripId = storageService.getCurrentTripId()
    val trip = mutableStateOf(Trip())

    fun initialize() {
        launchCatching {
            trip.value = storageService.getTrip(tripId) ?: Trip()
        }
    }

    fun popUpBackStack(openAndPopUp: (String) -> Unit) {
        openAndPopUp("menu")
    }
}