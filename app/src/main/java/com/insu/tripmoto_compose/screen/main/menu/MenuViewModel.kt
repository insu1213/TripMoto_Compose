package com.insu.tripmoto_compose.screen.main.menu

import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
): MyViewModel(logService) {
    fun onLogoutClick(callback: () -> Unit) {
        viewModelScope.launch {
            accountService.signOut()
            SnackbarManager.showMessage(R.string.logout)
            callback()
        }
    }

    fun onOtherTripClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TripSelectionScreen")
    }
}