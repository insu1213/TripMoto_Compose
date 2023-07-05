package com.insu.tripmoto_compose.screen.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel(){
    val showError = mutableStateOf(false)

    init {
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        openAndPopUp("LoginScreen", "SplashScreen")
    }
}