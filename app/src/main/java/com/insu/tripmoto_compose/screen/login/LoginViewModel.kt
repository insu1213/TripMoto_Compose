package com.insu.tripmoto_compose.screen.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.insu.tripmoto_compose.common.ext.isValidEmail
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel(){
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if(!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if(password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }
    }
}