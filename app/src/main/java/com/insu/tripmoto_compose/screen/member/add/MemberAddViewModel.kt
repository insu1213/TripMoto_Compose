package com.insu.tripmoto_compose.screen.member.add

import com.insu.tripmoto_compose.model.InviteCode
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MemberAddViewModel @Inject constructor(
    logService: LogService,
    val storageService: StorageService
): MyViewModel(logService) {

    val code = generateRandomString()


    private fun generateRandomString(): String { // 10자리 난수 생성 (같을 확를 0.00000000000000015%
        val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
        val code = (1..10)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        launchCatching {
            storageService.saveInviteCode(InviteCode(code = code, tripId = storageService.getCurrentTripId()))
        }

        return code
    }
}