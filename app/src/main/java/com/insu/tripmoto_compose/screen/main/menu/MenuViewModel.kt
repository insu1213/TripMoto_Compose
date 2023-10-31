package com.insu.tripmoto_compose.screen.main.menu

import android.location.LocationListener
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.MyAppState
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.network.ConnectionState
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.User
import com.insu.tripmoto_compose.model.UserInfo
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
//import com.insu.tripmoto_compose.model.service.NotificationService
import com.insu.tripmoto_compose.screen.MyViewModel
import com.loopj.android.http.RequestParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
): MyViewModel(logService) {

    private val currentUserId = accountService.currentUserId
    private val _nickName = MutableStateFlow("닉네임 정보를 불러오는 중...")
    private val _email = MutableStateFlow("이메일 정보를 불러오는 중...")

    var nickName: StateFlow<String> = _nickName.asStateFlow()
        private set
    var email: StateFlow<String> = _email.asStateFlow()
        private set

    init {
        getUidToNickName(currentUserId) { nickName, email ->
            _nickName.value = nickName
            _email.value = email
        }
    }

    fun onOtherTripClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TripSelectionScreen")
    }

    fun onTripSettingClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TravelManagementScreen")
    }

    fun onProfileSettingClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp("ProfileScreen")
    }

    private fun getUidToNickName(uid: String, callback: (String, String) -> Unit) {
        launchCatching {
            val userInfo = storageService.getUserInfo(uid) ?: UserInfo()

            callback(userInfo.nickName, userInfo.email)
        }
    }

//    fun onTriggerNotification(activity: ComponentActivity) {
//        val notificationService = NotificationService(activity.applicationContext)
//        notificationService.showBasicNotification("테스트 알림", "테스트 알림입니다.")
//    }
}