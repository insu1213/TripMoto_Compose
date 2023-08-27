package com.insu.tripmoto_compose.model

import com.google.firebase.firestore.DocumentId

data class ChatList (
    @DocumentId val id: String = "",
    val text: String = "",
    val userId: String = "",
    val nickName: String = "",
    val uploadTime: String = "",
    val readMembers: List<String> = listOf("")
)