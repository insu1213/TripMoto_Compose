package com.insu.tripmoto_compose.model.service.impl

import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.trace
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.insu.tripmoto_compose.model.service.ConfigurationService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.insu.tripmoto_compose.BuildConfig
import com.insu.tripmoto_compose.R.xml as AppConfig

class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {
    private val remoteConfig
        get() = Firebase.remoteConfig

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override suspend fun fetchConfiguration(): Boolean =
        trace(FETCH_CONFIG_TRACE) { remoteConfig.fetchAndActivate().await() }


    companion object {
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}
