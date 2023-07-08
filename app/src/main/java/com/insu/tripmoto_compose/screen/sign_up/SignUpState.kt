package com.insu.tripmoto_compose.screen.sign_up

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)