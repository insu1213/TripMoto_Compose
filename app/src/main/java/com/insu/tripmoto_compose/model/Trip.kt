package com.insu.tripmoto_compose.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.DocumentId


data class Trip(
    @DocumentId val id: String = "",
    val title: String = "",
    val region: String = "",
    val city: String = "",
    val administrator: String = "",
    val co_administrator: List<String> = listOf(""),
    val co_modifier: List<String> = listOf(""),
    val member: List<String> = listOf(""),
    val startDate: String = "",
    val endDate: String = "",
    val expenses: String = "",
    val room: String = "",
    val adult: String = "",
    val kid: String = "",
    val isEdit: Int = 0,
//    val marker: String, // markerUid { position,  }
//    val wishList: String, // wishListUid
)