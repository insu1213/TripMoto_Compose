package com.insu.tripmoto_compose.screen.fore

import java.util.*

data class ForeUiState(
    val nation: String = "",
    val city: String = "",

    val schedule_start: Date = Date(20230701),
    val schedule_end: Date = Date(20230701),

    val member_adult: Int = 0,
    val member_kids: Int = 0,
    val rooms: Int = 0,

    val expenses: Int = 0
)