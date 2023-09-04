package com.insu.tripmoto_compose.screen.loading

import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import javax.inject.Inject

class LoadingViewModel @Inject constructor(
    logService: LogService
): MyViewModel(logService) {
    fun onNextScreen(openAndPopUp: (String) -> Unit) {

    }
}