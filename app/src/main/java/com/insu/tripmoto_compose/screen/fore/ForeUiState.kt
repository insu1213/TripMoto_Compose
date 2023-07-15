package com.insu.tripmoto_compose.screen.fore

import java.util.*

data class ForeUiState(
    val nation: String = "",
    val city: String = "",

    val schedule_start: String = "",
    val schedule_end: String = "",

    val member_adult: String = "",
    val member_kids: String = "",
    val rooms: String = "",

    val expenses: String = ""
)