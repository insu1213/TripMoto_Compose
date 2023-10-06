package com.insu.tripmoto_compose.model.service

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

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

suspend fun downloadImageFromFirebaseStorage(imageName: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/$imageName")
            val imageUrl = imageRef.downloadUrl.await()
            val inputStream = URL(imageUrl.toString()).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.d("[Error]", "이미지를 불러올 수 없습니다.")
            e.printStackTrace()
            null
        }
    }
}

fun removeImageFromFirebaseStorage(imageName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val storageRef = FirebaseStorage.getInstance().reference
    val imagesRef = storageRef.child("images/$imageName")

    imagesRef.delete()
        .addOnSuccessListener {
            // 이미지 삭제 성공 시 호출되는 콜백
            onSuccess()
        }
        .addOnFailureListener { exception ->
            // 이미지 삭제 실패 시 호출되는 콜백
            onFailure(exception.localizedMessage ?: "Image deletion failed.")
        }
}