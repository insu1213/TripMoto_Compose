package com.insu.tripmoto_compose.model.service

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage

fun uploadImageToFirebaseStorage(
    uri: Uri,
    wishListId: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val storageRef = FirebaseStorage.getInstance().reference
    val imagesRef = storageRef.child("images/$wishListId")

    imagesRef.putFile(uri)
        .addOnSuccessListener {
            // 이미지 업로드 성공 시 호출되는 콜백
            onSuccess()
        }
        .addOnFailureListener { exception ->
            // 이미지 업로드 실패 시 호출되는 콜백
            onFailure(exception.localizedMessage ?: "Image upload failed.")
        }
}