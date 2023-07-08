package com.insu.tripmoto_compose.screen.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.insu.tripmoto_compose.common.ext.isValidEmail
import com.insu.tripmoto_compose.common.ext.isValidPassword
import com.insu.tripmoto_compose.common.ext.passwordMatches
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

class SignUpViewModel @Inject constructor(
): ViewModel() {
    var uiState = mutableStateOf(SignUpUiState())
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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(onpenAndPopUp: (String, String) -> Unit) {
        if(!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }
        if(!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }
        if(!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }
    }
}