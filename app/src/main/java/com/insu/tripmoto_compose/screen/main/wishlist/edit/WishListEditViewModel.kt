package com.insu.tripmoto_compose.screen.main.wishlist.edit

import androidx.annotation.IntegerRes
import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.common.ext.idFromParameter
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListEditViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
): MyViewModel(logService) {
    val wishList = mutableStateOf(WishList())

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

    fun onImageResourceChange(newImage: Int) {
        wishList.value = wishList.value.copy(imageRes = newImage) // 에러 발생 가능.
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        wishList.value = wishList.value.copy(flag = newFlagOption)
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedWishList = wishList.value
            if(editedWishList.id.isBlank()) {
                storageService.save(editedWishList)
            } else {
                storageService.update(editedWishList)
            }
            popUpScreen()
        }
    }
}