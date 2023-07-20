package com.insu.tripmoto_compose.screen.main

import androidx.activity.OnBackPressedCallback
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun DisableBackNavigation(activity: ComponentActivity) {
    DisposableEffect(Unit) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing to disable back navigation
            }
        }
        activity.onBackPressedDispatcher.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }
}