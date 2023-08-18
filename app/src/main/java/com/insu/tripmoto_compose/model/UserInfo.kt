package com.insu.tripmoto_compose.model

import com.google.firebase.firestore.DocumentId

data class UserInfo(
    @DocumentId val id: String = "",
    val nickName: String = "",
    val email: String = "",
)