package com.insu.tripmoto_compose.screen.splash

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthException
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.ConfigurationService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : MyViewModel(logService){
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        Log.d(TAG, "hasUser: ${accountService.hasUser}")
        if(accountService.hasUser) openAndPopUp("MainScreen", "SplashScreen")
        else openAndPopUp("LoginScreen", "SplashScreen")
    }

//    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
//        launchCatching(snackbar = false) {
//            try {
//                accountService.createAnonymousAccount()
//            } catch (ex: FirebaseAuthException) {
//                showError.value = true
//                Log.d(TAG, "ex: $ex")
//                throw ex
//            }
//            openAndPopUp("LoginScreen", "SplashScreen")
//        }
//    }
}