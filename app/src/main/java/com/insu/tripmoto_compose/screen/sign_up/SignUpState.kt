package com.insu.tripmoto_compose.screen.sign_up

data class SignUpUiState(
    val nickName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isImage: Boolean = false
)