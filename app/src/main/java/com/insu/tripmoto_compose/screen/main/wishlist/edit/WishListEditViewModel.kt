package com.insu.tripmoto_compose.screen.main.wishlist.edit

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.common.ext.idFromParameter
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.model.service.uploadImageToFirebaseStorage
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListEditViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
): MyViewModel(logService) {
    val wishList = mutableStateOf(WishList())
    var imageUri: Uri? = null

    fun initialize(wishListId: String) {
        launchCatching {
            if(wishListId != "-1") {
                wishList.value = storageService.getWishList(wishListId.idFromParameter()) ?: WishList()
            }
        }
    }

    fun onTitleChange(newValue: String) {
        wishList.value = wishList.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        wishList.value = wishList.value.copy(description = newValue)
    }
    // TODO: 날짜, 시간 추가

    fun onImageResourceChange(newImage: Uri) {
        imageUri = newImage
        wishList.value.isImage = true
        // TODO: 이미지 업로드 후 삭제 기능
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        wishList.value = wishList.value.copy(flag = newFlagOption)
    }

    fun uploadImage(context: Context, bitmap: (Bitmap) -> Unit) {
//        viewModelScope.launch {
//            val loadedBitmap = if (Build.VERSION.SDK_INT < 28) {
//                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri!!)
//            } else {
//                val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
//                ImageDecoder.decodeBitmap(source)
//            }
//
//            bitmap(loadedBitmap)
//        }
        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(imageUri!!)
                val options = BitmapFactory.Options()
                options.inSampleSize = 6 // 이미지 크기를 줄이기 위한 샘플링 비율 설정
                val loadedBitmap = BitmapFactory.decodeStream(inputStream, null, options)
                inputStream?.close()

                // 압축된 이미지를 비트맵 콜백으로 전달
                if (loadedBitmap != null) {
                    bitmap(loadedBitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedWishList = wishList.value
            var wishListId: String? = null

            if(editedWishList.id.isBlank()) {
                wishListId = storageService.saveWishList(editedWishList)
            } else {
                storageService.updateWishList(editedWishList)
            }

            if(imageUri != null && wishListId != null) {
                uploadImageToFirebaseStorage(
                    imageUri!!,
                    wishListId,
                    onSuccess = { popUpScreen() },
                    onFailure = { Log.d(TAG, "결과: 실패") },
                )
            } else {
                popUpScreen()
            }
        }
    }
}