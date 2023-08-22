package com.insu.tripmoto_compose.model

import com.google.firebase.firestore.DocumentId


data class Trip(
    @DocumentId val id: String = "",
    val title: String = "",
    val region: String = "",
    val city: String = "",
    val administrator: String = "",
    val member: List<String> = listOf(),
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