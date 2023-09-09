package com.insu.tripmoto_compose.model

import com.google.firebase.firestore.DocumentId

data class MapMarker(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val userId: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val completed: Boolean = false,
    val options: Int = 0,
    val iterator: Int = 0,
)