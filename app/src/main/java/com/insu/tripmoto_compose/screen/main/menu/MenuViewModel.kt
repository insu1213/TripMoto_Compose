package com.insu.tripmoto_compose.screen.main.menu

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
//import com.insu.tripmoto_compose.model.service.NotificationService
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

    fun onTripSettingClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TravelManagementScreen")
    }

//    fun onTriggerNotification(activity: ComponentActivity) {
//        val notificationService = NotificationService(activity.applicationContext)
//        notificationService.showBasicNotification("테스트 알림", "테스트 알림입니다.")
//    }
}