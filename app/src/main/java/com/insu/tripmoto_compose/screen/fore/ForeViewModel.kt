package com.insu.tripmoto_compose.screen.fore

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

@HiltViewModel
class ForeViewModel @Inject constructor(
    val storageService: StorageService,
    private val accountService: AccountService,
    logService: LogService
): MyViewModel(logService) {
    var uiState = mutableStateOf(ForeUiState())
        private set

    companion object {
        var uiData = Trip()
    }


    private val nation
        get() = uiState.value.nation
    private val city
        get() = uiState.value.city

    private val schedule_start
        get() = uiState.value.schedule_start
    private val schedule_end
        get() = uiState.value.schedule_end

    private val adult
        get() = uiState.value.member_adult
    private val kids
        get() = uiState.value.member_kids
    private val expenses
        get() = uiState.value.expenses
    private val title
        get() = uiState.value.title

    fun onTitleChange(newValue: String) {
        uiState.value = uiState.value.copy(title = newValue)
    }

    fun onNationChange(newValue: String) {
        // nation의 값이 변경되면 city의 값도 기본값으로 초기화
        uiState.value = uiState.value.copy(nation = newValue, city = "")
    }

    fun onCityChange(newValue: String) {
        uiState.value = uiState.value.copy(city = newValue)
    }

    fun onDateChange(newValue: String) {
        Log.d(TAG, "Date: $newValue")
        val first = newValue.split(" - ")[0]
        val second = newValue.split(" - ")[1]
        uiState.value = uiState.value.copy(schedule_start = first, schedule_end = second)
    }

    fun onAdultChange(newValue: String) {
        uiState.value = uiState.value.copy(member_adult = newValue)
    }
    fun onKidsChange(newValue: String) {
        uiState.value = uiState.value.copy(member_kids = newValue)
    }

    fun onExpensesChange(newValue: String) {
        uiState.value = uiState.value.copy(expenses = newValue)
    }

    fun titleOnNextClick(openAndPopUp: (String) -> Unit) {
        if(title.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_title_error)
            return
        }
        uiData = uiData.copy(title = title)
        openAndPopUp("TravelPlaceScreen")
    }

    fun placeOnNextClick(openAndPopUp: (String) -> Unit) {
        if(nation.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_nation_error)
            return
        }

        if(city.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_city_error)
            return
        }
        uiData = uiData.copy(region = nation, city = city)

        openAndPopUp("TravelScheduleScreen")
    }

    fun scheduleOnNextClick(openAndPopUp: (String) -> Unit) {
        if(schedule_start == "StartDate" || schedule_start.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_startdate_error)
            return
        }
        if(schedule_end == "EndDate" || schedule_end.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_enddate_error)
            return
        }
        uiData = uiData.copy(startDate = schedule_start, endDate = schedule_end)
        openAndPopUp("TravelMembersScreen")
    }

    fun membersOnNextClick(openAndPopUp: (String) -> Unit) {
        if(adult.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_adult_error)
            return
        }
        if(kids.isBlank()) {
            onKidsChange("0")
        }
        uiData = uiData.copy(adult = adult, kid = kids)
        openAndPopUp("TravelExpensesScreen")
    }

    fun expensesOnNextClick(openAndPopUp: (String) -> Unit) {
        if(expenses.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_expenses_error)
            return
        }
        if(expenses.toInt() < 10000) {
            SnackbarManager.showMessage(AppText.low_expenses_error)
            return
        }
        uiData = uiData.copy(expenses = expenses)
        val currentUserId = accountService.currentUserId
        uiData = uiData.copy(administrator = currentUserId)
        uiData = uiData.copy(member = mutableListOf(currentUserId))

        launchCatching {
            storageService.saveTrip(uiData)
        }


        openAndPopUp("TripSelectionScreen")
    }
}