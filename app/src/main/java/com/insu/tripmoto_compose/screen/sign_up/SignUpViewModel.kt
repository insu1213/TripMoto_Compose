package com.insu.tripmoto_compose.screen.sign_up

import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.common.ext.isValidEmail
import com.insu.tripmoto_compose.common.ext.isValidPassword
import com.insu.tripmoto_compose.common.ext.passwordMatches
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
): MyViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onNickNameChange(newValue: String) {
        uiState.value = uiState.value.copy(nickName = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
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

        launchCatching {
            accountService.linkAccount(email, password)
            openAndPopUp("LoginScreen", "SignUpScreen")
        }
    }

    fun goSignIn(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp("LoginScreen", "SignUpScreen")
    }
}