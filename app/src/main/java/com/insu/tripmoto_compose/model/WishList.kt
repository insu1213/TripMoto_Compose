package com.insu.tripmoto_compose.model

import android.graphics.Bitmap
import androidx.annotation.IntegerRes
import com.google.firebase.firestore.DocumentId

data class WishList(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    var isImage: Boolean = false,
    val userId: String = "",
    val completed: Boolean = false,
    val flag: Boolean = false
)