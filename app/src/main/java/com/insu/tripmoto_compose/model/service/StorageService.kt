package com.insu.tripmoto_compose.model.service

import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.model.UserInfo
import com.insu.tripmoto_compose.model.WishList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface StorageService {
    val trip: Flow<List<Trip>>
    val wishList: Flow<List<WishList>>
    val marker: Flow<List<MapMarker>>
    val chatList: Flow<List<ChatList>>
    val currentTripId: MutableState<String>


    val tripCollection: CollectionReference
    val tripDocument: DocumentReference

    suspend fun getTrip(tripId: String): Trip?
    suspend fun saveTrip(trip: Trip)
    suspend fun updateTrip(trip: Trip)
    suspend fun deleteTrip(tripId: String)

    suspend fun getUserInfo(uid: String): UserInfo?
    suspend fun saveUserInfo(uid: String, userInfo: UserInfo)
    suspend fun updateUserInfo(uid: String, userInfo: UserInfo)
    suspend fun deleteUserInfo(uid: String)

    suspend fun getWishList(wishListId: String): WishList?
    suspend fun saveWishList(wishList: WishList): String
    suspend fun  updateWishList(wishList: WishList)
    suspend fun deleteWishList(wishListId: String)


    suspend fun getMarker(markerId: String): MapMarker?
    suspend fun saveMarker(marker: MapMarker): String
    suspend fun updateMarker(marker: MapMarker)
    suspend fun deleteMarker(markerId: String)


    suspend fun getChatList(chatListId: String): ChatList?
    suspend fun saveChatList(chatList: ChatList): String
    suspend fun deleteChatList(chatListId: String)
}