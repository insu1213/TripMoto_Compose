package com.insu.tripmoto_compose.screen.main.wishlist

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.ConfigurationService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.model.service.removeImageFromFirebaseStorage
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
): MyViewModel(logService) {
    val options = mutableStateOf<List<String>>(listOf())

    val wishList = storageService.wishList

    fun loadWishListOptions() {
        val hasEditOption = configurationService.isShowWishListEditButtonConfig
        options.value = WishListActionOption.getOptions(hasEditOption)
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen("WishListEditScreen")


    fun onWishListActionClick(openScreen: (String) -> Unit, wishList: WishList, action: String) {
        when (WishListActionOption.getByTitle(action)) {
            WishListActionOption.EditWishList -> openScreen("WishListEditScreen?wishListId={${wishList.id}}")
            WishListActionOption.ToggleFlag -> onFlagWishListClick(wishList)
            WishListActionOption.DeleteWishList -> onDeleteWishListClick(wishList)
        }
    }

    fun onWishListCheckChange(wishList: WishList) {
        launchCatching { storageService.updateWishList(wishList.copy(completed = !wishList.completed)) }
    }

    private fun onFlagWishListClick(wishList: WishList) {
        launchCatching { storageService.updateWishList(wishList.copy(flag = !wishList.flag)) }
    }

    private fun onDeleteWishListClick(wishList: WishList) {
        launchCatching {
            storageService.deleteWishList(wishList.id)
            if(wishList.isImage) {
                removeImageFromFirebaseStorage(wishList.id,
                onSuccess = { Log.d(TAG, "이미지를 삭제하는데 성공하였습니다.") },
                onFailure = { Log.d(TAG, "이미지를 삭제하는데 실패하였습니다.") }
                )
            }
        }
    }
}