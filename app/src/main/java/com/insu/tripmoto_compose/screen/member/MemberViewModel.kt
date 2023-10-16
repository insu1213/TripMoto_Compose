package com.insu.tripmoto_compose.screen.member

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    val storageService: StorageService,
    val accountService: AccountService,
    logService: LogService
): MyViewModel(logService) {

    private var memberNickNameList = mutableListOf<String>()

    fun getMember(callback: (List<String>) -> Unit) {
        memberNickNameList = mutableListOf()
        viewModelScope.launch {
            val memberList = storageService.getTrip(storageService.getCurrentTripId())?.member
            memberList?.forEach { member ->
                val userInfo = uidToNickName(member)
                memberNickNameList.add(userInfo.nickName)
            }
            Log.d(TAG, "member2: $memberNickNameList")
            callback(memberNickNameList)
        }
    }

    fun getMemberNickNameList(): List<String> {
        return memberNickNameList
    }

    private suspend fun uidToNickName(uid: String): UserInfo {
        return storageService.getUserInfo(uid) ?: UserInfo()
    }

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("MemberAddScreen")
    }
}