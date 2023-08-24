package com.insu.tripmoto_compose.model

import com.google.firebase.firestore.DocumentId

data class InviteCode(
    @DocumentId val id: String = "",
    val code: String = "",
    val tripId: String = ""
)