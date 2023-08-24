package com.insu.tripmoto_compose.screen.member

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.model.UserInfo
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    val storageService: StorageService,
    val accountService: AccountService,
    logService: LogService
): MyViewModel(logService) {


    suspend fun getMember(): List<String> = coroutineScope {
        val memberNickNameList = mutableListOf<String>()

        val memberList = storageService.getTrip(storageService.getCurrentTripId())?.member
        if (memberList != null) {
            val deferredList = memberList.map { member ->
                async {
                    uidToNickName(member) { userInfo ->
                        memberNickNameList.add(userInfo.nickName)
                    }
                }
            }
            deferredList.awaitAll()
        }

        memberNickNameList
    }

    private fun uidToNickName(uid: String, callback: (UserInfo) -> Unit) {
        viewModelScope.launch {
            val userInfo = storageService.getUserInfo(uid)
            withContext(Dispatchers.Main) {
                callback(userInfo?: UserInfo())
            }
        }
    }

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("MemberAddScreen")
    }
}