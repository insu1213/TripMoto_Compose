package com.insu.tripmoto_compose.model

import com.google.firebase.firestore.DocumentId

data class MapMarker(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val userId: String = "",
    val lat: Float = 0f,
    val lng: Float = 0f,
    val completed: Boolean = false,
)