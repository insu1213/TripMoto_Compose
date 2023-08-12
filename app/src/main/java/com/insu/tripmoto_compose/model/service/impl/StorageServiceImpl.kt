package com.insu.tripmoto_compose.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.perf.ktx.trace
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.model.User
import com.insu.tripmoto_compose.model.UserInfo
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.main.BottomNavItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val auth: AccountService
): StorageService {


    // WishList
    @OptIn(ExperimentalCoroutinesApi::class)
    override val wishList: Flow<List<WishList>>
        get() =
            auth.currentUser.flatMapLatest { user: User ->
                firestore.collection(WISH_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
        }
    override suspend fun getWishList(wishListId: String): WishList? =
        firestore.collection(WISH_COLLECTION).document(wishListId).get().await().toObject()
    override suspend fun saveWishList(wishList: WishList): String =
        trace(SAVE_WISH_TRACE) {
            val wishWithUserId = wishList.copy(userId = auth.currentUserId)
            firestore.collection(WISH_COLLECTION).add(wishWithUserId).await().id
        }
    override suspend fun updateWishList(wishList: WishList): Unit =
        trace(UPDATE_WISH_TRACE) {
            firestore.collection(WISH_COLLECTION).document(wishList.id).set(wishList).await()
        }
    override suspend fun deleteWishList(wishListId: String) {
        firestore.collection(WISH_COLLECTION).document(wishListId).delete().await()
    }



    // Google Map Marker
    @OptIn(ExperimentalCoroutinesApi::class)
    override val marker: Flow<List<MapMarker>>
        get() =
            auth.currentUser.flatMapLatest { user: User ->
                firestore.collection(MARKER_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
            }
    override suspend fun getMarker(markerId: String): MapMarker? =
        firestore.collection(MARKER_COLLECTION).document(markerId).get().await().toObject()

    override suspend fun saveMarker(marker: MapMarker): String =
        trace(SAVE_MARKER_TRACE) {
            val markerWithUserId = marker.copy(userId = auth.currentUserId)
            firestore.collection(MARKER_COLLECTION).add(markerWithUserId).await().id
        }
    override suspend fun updateMarker(marker: MapMarker): Unit =
        trace(UPDATE_MARKER_TRACE) {
            firestore.collection(MARKER_COLLECTION).document(marker.id).set(marker).await()
        }
    override suspend fun deleteMarker(markerId: String) {
        firestore.collection(MARKER_COLLECTION).document(markerId).delete().await()
    }


    // ChatList
    @OptIn(ExperimentalCoroutinesApi::class)
    override val chatList: Flow<List<ChatList>>
        get() =
            auth.currentUser.flatMapLatest { user: User ->
                firestore.collection(CHAT_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
            }

    override suspend fun getChatList(chatListId: String): ChatList? =
        firestore.collection(CHAT_COLLECTION).document(chatListId).get().await().toObject()
    override suspend fun saveChatList(chatList: ChatList): String =
        trace(SAVE_CHAT_TRACE) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            val currentTime = dateFormat.format(Date(System.currentTimeMillis()))
            val chatWithUserIdAndTime = chatList.copy(userId = auth.currentUserId, uploadTime = currentTime)

            firestore.collection(CHAT_COLLECTION).add(chatWithUserIdAndTime).await().id
        }
    override suspend fun deleteChatList(chatListId: String) {
        firestore.collection(CHAT_COLLECTION).document(chatListId).delete().await()
    }


    // UserInfo
    override suspend fun getUserInfo(uid: String): UserInfo? =
        firestore.collection(USERINFO_COLLECTION).document(uid).get().await().toObject()
    override suspend fun saveUserInfo(uid: String, userInfo: UserInfo) {
        trace(SAVE_USERINFO_TRACE) {
            firestore.collection(USERINFO_COLLECTION).document(uid).set(userInfo).await()
        }
    }
    override suspend fun updateUserInfo(uid: String, userInfo: UserInfo) {
        trace(UPDATE_USERINFO_TRACE) {
            firestore.collection(USERINFO_COLLECTION).document(uid).set(userInfo).await()
        }
    }
    override suspend fun deleteUserInfo(uid: String) {
        firestore.collection(USERINFO_COLLECTION).document(uid).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"

        private const val WISH_COLLECTION = "wishList"
        private const val SAVE_WISH_TRACE = "saveWishList"
        private const val UPDATE_WISH_TRACE = "updateWishList"

        private const val MARKER_COLLECTION = "marker"
        private const val SAVE_MARKER_TRACE = "saveMarker"
        private const val UPDATE_MARKER_TRACE = "updateMarker"

        private const val CHAT_COLLECTION = "chatList"
        private const val SAVE_CHAT_TRACE = "saveChatList"

        private const val USERINFO_COLLECTION = "userInfo"
        private const val SAVE_USERINFO_TRACE = "saveUserInfo"
        private const val UPDATE_USERINFO_TRACE = "updateUserInfo"
    }
}