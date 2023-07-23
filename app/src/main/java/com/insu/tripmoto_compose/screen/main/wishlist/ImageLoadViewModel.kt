package com.insu.tripmoto_compose.screen.main.wishlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.model.service.downloadImageFromFirebaseStorage
import kotlinx.coroutines.launch

class ImageLoadViewModel : ViewModel() {
    private val imageCache = mutableStateMapOf<String, State<ImageBitmap?>?>()

    fun getImageBitmap(imageName: String): State<ImageBitmap?>? {
        if (imageCache.containsKey(imageName)) {
            return imageCache[imageName]
        }

        val imageState = mutableStateOf<ImageBitmap?>(null)

        viewModelScope.launch {
            val bitmap = downloadImageFromFirebaseStorage(imageName)
            // 이미지 다운로드 후 캐시에 저장
            imageCache[imageName] = imageState
            // 이미지 변경 감지
            imageState.value = bitmap?.asImageBitmap()
        }

        // 이미지를 다운로드하는 동안 null을 반환하고, 다운로드 후 이미지로 업데이트 됨
        return imageState
    }
}