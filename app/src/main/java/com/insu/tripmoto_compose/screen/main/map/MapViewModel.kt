package com.insu.tripmoto_compose.screen.main.map

import androidx.compose.runtime.mutableStateOf
import com.insu.tripmoto_compose.model.service.ConfigurationService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
): MyViewModel(logService) {
    val marker = storageService.marker


}