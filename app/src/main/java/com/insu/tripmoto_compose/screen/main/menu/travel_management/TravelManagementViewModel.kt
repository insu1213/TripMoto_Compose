package com.insu.tripmoto_compose.screen.main.menu.travel_management

import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelManagementViewModel @Inject constructor(
    logService: LogService,
    val accountService: AccountService,
    val storageService: StorageService
): MyViewModel(logService) {
    private val tripId = storageService.getCurrentTripId()
    private val userId = accountService.currentUserId
    val trip = mutableStateOf(Trip())

    fun initialize() {
        launchCatching {
            trip.value = storageService.getTrip(tripId) ?: Trip()
        }
    }

    fun popUpBackStack(openAndPopUp: (String) -> Unit) {
        openAndPopUp("menu")
    }

    fun exitTrip(openAndPopUp: (String) -> Unit) {
        Log.d(TAG, "userId: $userId")
        if(trip.value.co_administrator.contains(userId)) {
            val idx = trip.value.co_administrator.indexOf(userId)
            val newRole = trip.value.co_administrator.toMutableList()
            newRole.removeAt(idx)
            trip.value = trip.value.copy(co_administrator = newRole)
        }
        if(trip.value.co_modifier.contains(userId)) {
            val idx = trip.value.co_modifier.indexOf(userId)
            val newRole = trip.value.co_modifier.toMutableList()
            newRole.removeAt(idx)
            trip.value = trip.value.copy(co_modifier = newRole)
        }
        if(trip.value.member.contains(userId)) {
            val idx = trip.value.member.indexOf(userId)
            val newRole = trip.value.member.toMutableList()
            newRole.removeAt(idx)
            trip.value = trip.value.copy(member = newRole)
        }



        viewModelScope.launch {
            storageService.updateTrip(trip.value)
            openAndPopUp("TripSelectionScreen")
        }

    }
}