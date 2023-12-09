package com.insu.tripmoto_compose.screen.member

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.model.Trip
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

    private var memberInfoList = mutableListOf<UserInfo>()
    var trip: Trip = Trip()

    init {
        viewModelScope.launch {
            trip = storageService.getTrip(storageService.getCurrentTripId()) ?: Trip()
        }
    }

    fun getMember(callback: (List<UserInfo>) -> Unit) {
        memberInfoList = mutableListOf()
        viewModelScope.launch {
            val memberList = storageService.getTrip(storageService.getCurrentTripId())?.member
            memberList?.forEach { member ->
                val userInfo = uidToNickName(member)
                memberInfoList.add(userInfo)
            }
            Log.d(TAG, "member2: $memberList")
            callback(memberInfoList)
        }
    }

    fun popUpBackStack(openAndPopUp: (String) -> Unit) {
        openAndPopUp("TravelManagementScreen")
    }

    fun getMemberNickNameList(): List<UserInfo> {
        return memberInfoList
    }

    fun checkPermission(uid: String, callback: (String) -> Unit) {
        if(trip.administrator == uid) {
            callback("그룹장")
        } else if(trip.co_administrator == uid) {
            callback("공동 그룹장")
        } else if(trip.co_modifier == uid) {
            callback("공동 수정자")
        } else {
            callback("")
        }
    }

    private suspend fun uidToNickName(uid: String): UserInfo {
        return storageService.getUserInfo(uid) ?: UserInfo()
    }

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("MemberAddScreen")
    }
}