package com.insu.tripmoto_compose.screen.main.wishlist.edit

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.ext.idFromParameter
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.model.service.uploadImageToFirebaseStorage
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedWishList = wishList.value
            var wishListId: String? = null

            if(editedWishList.id.isBlank()) {
                wishListId = storageService.save(editedWishList)
            } else {
                storageService.update(editedWishList)
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