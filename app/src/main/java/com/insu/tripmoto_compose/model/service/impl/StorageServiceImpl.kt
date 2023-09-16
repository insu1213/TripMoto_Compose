package com.insu.tripmoto_compose.model.service.impl

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.perf.ktx.trace
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.InviteCode
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.model.Trip
import com.insu.tripmoto_compose.model.User
import com.insu.tripmoto_compose.model.UserInfo
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.model.service.StorageService.Companion.currentTripId
import com.insu.tripmoto_compose.screen.main.BottomNavItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val auth: AccountService
): StorageService {

    override val tripCollection = firestore.collection(TRIP_COLLECTION)
    override val tripDocument = tripCollection.document()

    override fun getCurrentTripId(): String = currentTripId.value

    override fun updateCurrentTripId(tripId: String) {
        currentTripId.value = tripId
        Log.d(TAG, "current: ${currentTripId.value}")
    }

    // InviteCode
    override val inviteCode: Flow<List<InviteCode>>
        get() =
            firestore.collection(INVITE_CODE_COLLECTION).dataObjects()

    override suspend fun findInviteCode(code: String): InviteCode? =
        firestore.collection(INVITE_CODE_COLLECTION).whereEqualTo("code", code).get().await().first().toObject()

    override suspend fun findTripUid(tripId: String): InviteCode? =
        try {
            firestore.collection(INVITE_CODE_COLLECTION).whereEqualTo("tripId", tripId).get().await().first().toObject()
        } catch(e: Exception) {
            InviteCode()
        }

    override suspend fun getInviteCode(inviteCodeId: String): InviteCode? =
        firestore.collection(INVITE_CODE_COLLECTION).document(inviteCodeId).get().await().toObject()

    override suspend fun saveInviteCode(inviteCode: InviteCode) {
        firestore.collection(INVITE_CODE_COLLECTION).add(inviteCode).await().id
    }

    override suspend fun updateInviteCode(inviteCode: InviteCode) {
        firestore.collection(INVITE_CODE_COLLECTION).document(inviteCode.id).set(wishList).await()
    }

    override suspend fun deleteInviteCode(inviteCode: InviteCode) {
        firestore.collection(INVITE_CODE_COLLECTION).document(inviteCode.id).delete().await()
    }


    // Trip
    @OptIn(ExperimentalCoroutinesApi::class)
    override val trip: Flow<List<Trip>>
        get() =
            auth.currentUser.flatMapLatest { user: User ->
                //tripCollection.whereEqualTo(MEMBER_UID_FIELD, listOf(user.id)).dataObjects()
                tripCollection.whereArrayContains(MEMBER_UID_FIELD, user.id).dataObjects()
            }

    override suspend fun getTrip(tripId: String): Trip? =
        //firestore.collection(TRIP_COLLECTION).document(wishListId).get().await().toObject()
        tripCollection.document(tripId).get().await().toObject()
    override suspend fun saveTrip(trip: Trip) {
        trace(SAVE_TRIP_TRACE) {
            tripCollection.add(trip).await().id
        }
    }

    override suspend fun updateTrip(trip: Trip) {
        trace(UPDATE_TRIP_TRACE) {
            tripCollection.document(trip.id).set(trip).await()
        }
    }
    override suspend fun deleteTrip(tripId: String) {
        tripCollection.document(tripId).delete().await()
    }

    // WishList
    @OptIn(ExperimentalCoroutinesApi::class)
    override val wishList: Flow<List<WishList>>
        get() =
//            auth.currentUser.flatMapLatest { user: User ->
//                firestore.collection(WISH_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
//            }
            tripCollection.document(currentTripId.value).collection(WISH_COLLECTION).dataObjects()

    override suspend fun getWishList(wishListId: String): WishList? =
        firestore.collection(WISH_COLLECTION).document(wishListId).get().await().toObject()
    override suspend fun saveWishList(wishList: WishList): String =
        trace(SAVE_WISH_TRACE) {
            val wishWithUserId = wishList.copy(userId = auth.currentUserId)
            tripCollection.document(currentTripId.value).collection(WISH_COLLECTION).add(wishWithUserId).await().id
//            val data = hashMapOf(
//                "wishList" to wishWithUserId
//            )
//            firestore.collection(WISH_COLLECTION).add(data).await().id
        }
    override suspend fun updateWishList(wishList: WishList): Unit =
        trace(UPDATE_WISH_TRACE) {
            //firestore.collection(WISH_COLLECTION).document(wishList.id).set(wishList).await()
            tripCollection.document(currentTripId.value).collection(WISH_COLLECTION).document(wishList.id).set(wishList).await()
        }
    override suspend fun deleteWishList(wishListId: String) {
        //firestore.collection(WISH_COLLECTION).document(wishListId).delete().await()
        tripCollection.document(currentTripId.value).collection(WISH_COLLECTION).document(wishListId).delete().await()
    }

    // Google Map Marker
    @OptIn(ExperimentalCoroutinesApi::class)
    override val marker: Flow<List<MapMarker>>
        get()
//            auth.currentUser.flatMapLatest { user: User ->
//                firestore.collection(MARKER_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
//            }
        {
            Log.d(TAG, "currentTripId: ${currentTripId.value}")
            return tripCollection.document(currentTripId.value).collection(MARKER_COLLECTION).dataObjects()
        }
    override suspend fun getMarker(markerId: String): MapMarker? =
        //firestore.collection(MARKER_COLLECTION).document(markerId).get().await().toObject()
        tripCollection.document(currentTripId.value).collection(MARKER_COLLECTION).document(markerId).get().await().toObject()

    override suspend fun saveMarker(marker: MapMarker): String =
        trace(SAVE_MARKER_TRACE) {


            val utcTimeZone = TimeZone.getTimeZone("Asia/Seoul")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dateFormat.timeZone = utcTimeZone
            val currentDate = Date()
            val formattedDate = dateFormat.format(currentDate)

            val markerWithUserIdAndDate = marker.copy(userId = auth.currentUserId, uploadTime = formattedDate)

            tripCollection.document(currentTripId.value).collection(MARKER_COLLECTION).add(markerWithUserIdAndDate).await().id
            //firestore.collection(MARKER_COLLECTION).add(markerWithUserId).await().id
        }
    override suspend fun updateMarker(marker: MapMarker): Unit =
        trace(UPDATE_MARKER_TRACE) {
            tripCollection.document(currentTripId.value).collection(MARKER_COLLECTION).document(marker.id).set(marker).await()
            //firestore.collection(MARKER_COLLECTION).document(marker.id).set(marker).await()
        }
    override suspend fun deleteMarker(markerId: String) {
        tripCollection.document(currentTripId.value).collection(MARKER_COLLECTION).document(markerId).delete().await()
        //firestore.collection(MARKER_COLLECTION).document(markerId).delete().await()
    }

    // ChatList
    @OptIn(ExperimentalCoroutinesApi::class)
    override val chatList: Flow<List<ChatList>>
        get() =
//            auth.currentUser.flatMapLatest { user: User ->
//                firestore.collection(CHAT_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
//            }
            tripCollection.document(currentTripId.value).collection(CHAT_COLLECTION).dataObjects()

    override suspend fun getChatList(chatListId: String): ChatList? =
        //firestore.collection(CHAT_COLLECTION).document(chatListId).get().await().toObject()
        tripCollection.document(currentTripId.value).collection(CHAT_COLLECTION).document(chatListId).get().await().toObject()
    override suspend fun saveChatList(chatList: ChatList): String =
        trace(SAVE_CHAT_TRACE) {
            val utcTimeZone = TimeZone.getTimeZone("Asia/Seoul")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dateFormat.timeZone = utcTimeZone
            val currentDate = Date()
            val formattedDate = dateFormat.format(currentDate)

            val chatWithUserIdAndTime = chatList.copy(userId = auth.currentUserId, uploadTime = formattedDate)

            tripCollection.document(currentTripId.value).collection(CHAT_COLLECTION).add(chatWithUserIdAndTime).await().id
            //firestore.collection(CHAT_COLLECTION).add(chatWithUserIdAndTime).await().id
        }

    override suspend fun updateChatList(chatList: ChatList) {
        tripCollection.document(currentTripId.value).collection(CHAT_COLLECTION).document(chatList.id).set(chatList).await()
    }

    override suspend fun deleteChatList(chatListId: String) {
        //firestore.collection(CHAT_COLLECTION).document(chatListId).delete().await()
        tripCollection.document(currentTripId.value).collection(CHAT_COLLECTION).document(chatListId).delete().await()
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
        private const val MEMBER_UID_FIELD = "member"

        private const val INVITE_CODE_COLLECTION = "inviteCode"

        private const val TRIP_COLLECTION = "trip"
        private const val SAVE_TRIP_TRACE = "saveTrip"
        private const val UPDATE_TRIP_TRACE = "updateTrip"

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