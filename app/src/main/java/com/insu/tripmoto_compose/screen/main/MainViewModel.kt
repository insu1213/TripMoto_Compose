package com.insu.tripmoto_compose.screen.main

import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    logService: LogService
): MyViewModel(logService) {
    fun goToLogin(openAndPopUp: (String) -> Unit) {

    }
}