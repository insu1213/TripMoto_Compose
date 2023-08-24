package com.insu.tripmoto_compose.screen.member.add

import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemberAddViewModel @Inject constructor(
    logService: LogService
): MyViewModel(logService) {

}