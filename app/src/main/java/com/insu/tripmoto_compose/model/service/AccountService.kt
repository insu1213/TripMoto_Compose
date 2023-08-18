package com.insu.tripmoto_compose.model.service

import com.insu.tripmoto_compose.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    //suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String, onSuccess: () -> Unit)
    suspend fun deleteAccount()
    suspend fun signOut()
}