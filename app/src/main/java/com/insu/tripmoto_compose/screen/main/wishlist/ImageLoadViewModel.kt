package com.insu.tripmoto_compose.screen.main.wishlist

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.downloadImageFromFirebaseStorage
import kotlinx.coroutines.launch

class ImageLoadViewModel : ViewModel() {
    private val imageCache = mutableStateMapOf<String, State<ImageBitmap?>?>()
    var imageDownloadState = mutableListOf(mapOf<String, ImageBitmap?>())
        private set

    fun getImageBitmap(id: String): State<ImageBitmap?>? {
        val imageState = mutableStateOf<ImageBitmap?>(null)

        if (imageCache.containsKey(id)) {
            return imageCache[id]
        }

        Log.d(TAG, "id: $id")

        viewModelScope.launch {
            val bitmap = downloadImageFromFirebaseStorage(id)
            // 이미지 다운로드 후 캐시에 저장
            imageCache[id] = imageState
            // 이미지 변경 감지
            imageState.value = bitmap?.asImageBitmap()
        }

        imageDownloadState.add(mapOf(id to imageState.value))

        // 이미지를 다운로드하는 동안 null을 반환하고, 다운로드 후 이미지로 업데이트 됨
        return imageState
    }
}