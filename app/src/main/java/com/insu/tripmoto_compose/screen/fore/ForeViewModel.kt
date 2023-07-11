package com.insu.tripmoto_compose.screen.fore

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun onNationChange(newValue: String) {
        // nation의 값이 변경되면 city의 값도 기본값으로 초기화
        uiState.value = uiState.value.copy(nation = newValue, city = "")
    }

    fun onCityChange(newValue: String) {
        uiState.value = uiState.value.copy(city = newValue)
    }

    fun onNextClick(openAndPopUp: (String, String) -> Unit) {
        if(nation.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_nation_error)
        }

        if(city.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_city_error)
        }

    }
}