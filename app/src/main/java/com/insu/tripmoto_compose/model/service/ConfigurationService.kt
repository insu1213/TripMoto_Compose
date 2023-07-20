package com.insu.tripmoto_compose.model.service

interface ConfigurationService {
    suspend fun fetchConfiguration(): Boolean
    val isShowWishListEditButtonConfig: Boolean
}