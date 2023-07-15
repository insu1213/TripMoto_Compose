package com.insu.tripmoto_compose.screen.fore

import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

@HiltViewModel
class ForeViewModel @Inject constructor(
    logService: LogService
): MyViewModel(logService) {
    var uiState = mutableStateOf(ForeUiState())
        private set

    private val nation
        get() = uiState.value.nation
    private val city
        get() = uiState.value.city

    private val schedule_start
        get() = uiState.value.schedule_start
    private val schedule_end
        get() = uiState.value.schedule_end

    fun onNationChange(newValue: String) {
        // nation의 값이 변경되면 city의 값도 기본값으로 초기화
        uiState.value = uiState.value.copy(nation = newValue, city = "")
    }

    fun onCityChange(newValue: String) {
        uiState.value = uiState.value.copy(city = newValue)
    }

    fun onDateChange(newValue: Pair<String, String>) {
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = newValue?.first ?: 0
//        val startDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
//        calendar.timeInMillis = newValue?.second ?: 0
//        val endDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
        uiState.value = uiState.value.copy(schedule_start = newValue.first, schedule_end = newValue.second)
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

        openAndPopUp("TravelScheduleScreen")
    }
}