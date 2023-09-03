package com.insu.tripmoto_compose.screen.settings

import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    val accountService: AccountService
): MyViewModel(logService) {
    fun signOut(callback: () -> Unit) {
        launchCatching {
            accountService.signOut()
            callback()
        }
    }
}