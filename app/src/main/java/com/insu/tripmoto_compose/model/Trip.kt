package com.insu.tripmoto_compose.model

import com.google.firebase.firestore.DocumentId


data class Trip(
    @DocumentId val id: String,
    val title: String = "",
    val region: String = "",
    val city: String = "",
    val administrator: String = "",
    val member: List<String> = listOf(),
    val expenses: Int = 0,
    val adult: Int = 0,
    val kid: Int = 0,
    val isEdit: Int = 0,
//    val marker: String, // markerUid { position,  }
//    val wishList: String, // wishListUid
)