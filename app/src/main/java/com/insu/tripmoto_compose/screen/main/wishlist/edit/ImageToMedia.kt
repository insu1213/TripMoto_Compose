package com.insu.tripmoto_compose.screen.main.wishlist.edit

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.IOException

fun uploadImageToMediaStore(contentResolver: ContentResolver, bitmap: ImageBitmap, title: String, description: String): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, title)
        put(MediaStore.Images.Media.DESCRIPTION, description)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    var uri: Uri? = null
    try {
        uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let { imageUri ->
            val outputStream = contentResolver.openOutputStream(imageUri)
            outputStream?.use { stream ->
                bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return uri
}