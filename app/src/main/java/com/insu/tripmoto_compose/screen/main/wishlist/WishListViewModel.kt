package com.insu.tripmoto_compose.screen.main.wishlist

import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.common.ext.idFromParameter
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
): MyViewModel(logService) {

//    val wishList = mutableStateOf(WishList())
//
//    fun initialize(wishListId: String) {
//        launchCatching {
//            if(wishListId != "-1") {
//                wishList.value = storageService.getWishList(wishListId.idFromParameter()) ?: WishList()
//            }
//        }
//    }

    val wishList = storageService.wishList

    fun onWishListActionClick(openScreen: (String) -> Unit, wishList: WishList, action: String) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
            TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
        }
    }

    fun onWishListCheckChange(wishList: WishList) {
        launchCatching { storageService.update(wishList.copy(completed = !wishList.completed)) }
    }

    private fun onFlagWishListClick(wishList: WishList) {
        launchCatching { storageService.update(wishList.copy(flag = !wishList.flag)) }
    }

    private fun onDeleteWishListClick(wishList: WishList) {
        launchCatching { storageService.delete(wishList.id) }
    }
}