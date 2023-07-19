package com.insu.tripmoto_compose.model.service

import com.insu.tripmoto_compose.model.WishList
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val wishList: Flow<List<WishList>>

    suspend fun getWishList(wishListId: String): WishList?

    suspend fun save(wishList: WishList): String

    suspend fun  update(wishList: WishList)

    suspend fun delete(wishListId: String)
}