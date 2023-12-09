package com.insu.tripmoto_compose.screen.member

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
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
            callback("관리자")
        } else if(trip.co_administrator.contains(uid)) {
            callback("공동 관리자")
        } else if(trip.co_modifier.contains(uid)) {
            callback("수정자")
        } else {
            callback("")
        }
    }

    fun changePermission(uid: String, role: String) {

        if(role == "공동 관리자") {
            if(trip.co_modifier.contains(uid)) {
                val idx = trip.co_modifier.indexOf(uid)
                val newRole = trip.co_modifier.toMutableList()
                newRole.removeAt(idx)
                trip = trip.copy(co_modifier = newRole)
            }

            if(trip.co_administrator.contains(uid)) { // 이미 참가 상태인 경우
                SnackbarManager.showMessage(R.string.already_part)
            } else {
                val newRole = trip.co_administrator.plus(uid)
                trip = trip.copy(co_administrator = newRole)
            }
        } else if(role == "수정자") {
            if (trip.co_administrator.contains(uid)){
                val idx = trip.co_administrator.indexOf(uid)
                val newRole = trip.co_administrator.toMutableList()
                newRole.removeAt(idx)
                trip = trip.copy(co_administrator = newRole)
            }
            if(trip.co_modifier.contains(uid)) {
                SnackbarManager.showMessage(R.string.already_part)
            } else {
                val newRole = trip.co_modifier.plus(uid)
                trip = trip.copy(co_modifier = newRole)
            }
        } else if(role == "그룹원") {
            if(trip.co_modifier.contains(uid)) {
                val idx = trip.co_modifier.indexOf(uid)
                val newRole = trip.co_modifier.toMutableList()
                newRole.removeAt(idx)
                trip = trip.copy(co_modifier = newRole)

            } else if (trip.co_administrator.contains(uid)){
                val idx = trip.co_administrator.indexOf(uid)
                val newRole = trip.co_administrator.toMutableList()
                newRole.removeAt(idx)
                trip = trip.copy(co_administrator = newRole)
            }
        }

        viewModelScope.launch {
            storageService.updateTrip(trip)
        }
    }

    private suspend fun uidToNickName(uid: String): UserInfo {
        return storageService.getUserInfo(uid) ?: UserInfo()
    }

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("MemberAddScreen")
    }
}