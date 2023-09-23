package com.insu.tripmoto_compose.screen.travel_option

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.InviteCode
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

@HiltViewModel
class TravelOptionViewModel @Inject constructor(
    logService: LogService,
    val storageService: StorageService,
    val accountService: AccountService
): MyViewModel(logService) {
    val code = mutableStateOf("")

    fun onNewTripClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TravelTitleScreen")
    }

    fun goToTripSelection(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TripSelectionScreen")
    }

    fun onDoneClick(dismiss: (Boolean) -> Unit) {
        launchCatching {
            val inviteCode = storageService.findInviteCode(code.value) ?: InviteCode()
            if(inviteCode.tripId.isNotBlank()) {
                var trip = storageService.getTrip(inviteCode.tripId)
                if (trip != null) {
                    val userId = accountService.currentUserId
                    if(trip.member.contains(userId)) { // 이미 참가 상태인 경우
                        SnackbarManager.showMessage(AppText.already_part)
                        dismiss(false)
                    }
                    val memberList = trip.member.plus(userId)
                    trip = trip.copy(member = memberList)
                    Log.d(TAG, "title: ${trip}")
                    storageService.updateTrip(trip)
                } else {
                    Log.d(TAG, "[Error] Trip is null!")
                }
            }
            withContext(Dispatchers.Main) {
                dismiss(inviteCode.tripId.isNotBlank())
            }
        }
    }

    fun onChangeCode(newValue: String) {
        code.value = newValue
    }
}