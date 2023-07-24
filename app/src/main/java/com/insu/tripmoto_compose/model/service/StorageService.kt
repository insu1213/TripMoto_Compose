package com.insu.tripmoto_compose.model.service

import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.model.WishList
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val wishList: Flow<List<WishList>>
    val marker: Flow<List<MapMarker>>

    suspend fun getWishList(wishListId: String): WishList?
    suspend fun saveWishList(wishList: WishList): String
    suspend fun  updateWishList(wishList: WishList)
    suspend fun deleteWishList(wishListId: String)


    suspend fun getMarker(markerId: String): MapMarker?
    suspend fun saveMarker(marker: MapMarker): String
    suspend fun updateMarker(marker: MapMarker)
    suspend fun deleteMarker(markerId: String)
}