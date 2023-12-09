package com.insu.tripmoto_compose.screen.member.add

import android.content.ClipData

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.model.InviteCode
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MemberAddViewModel @Inject constructor(
    logService: LogService,
    val storageService: StorageService
): MyViewModel(logService) {

    var code = mutableStateOf("")
    init {
        generateRandomString {
            Log.d(TAG, "code = $it")
            code.value = it
        }
    }

    fun copyText(clipManager: ClipboardManager) {
        clipManager.setText(AnnotatedString(code.value))
    }

    fun popUpBackStack(openAndPopUp: () -> Unit) {
        openAndPopUp()
    }

    private fun generateRandomString(callback: (String) -> Unit) { // 10자리 난수 생성 (같을 확를 0.00000000000000015%
        var code: String = ""
        viewModelScope.launch {
            val inviteCode = storageService.findTripUid(storageService.getCurrentTripId()) ?: InviteCode()
            if(inviteCode.tripId.isBlank()) {
                val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
                code = (1..10)
                    .map { Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")
                storageService.saveInviteCode(InviteCode(code = code, tripId = storageService.getCurrentTripId()))
                callback(code)
            } else {
                code = inviteCode.code
                callback(code)
            }
        }

    }


}