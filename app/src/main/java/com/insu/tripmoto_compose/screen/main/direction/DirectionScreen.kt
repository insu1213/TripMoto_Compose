package com.insu.tripmoto_compose.screen.main.direction

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.insu.tripmoto_compose.rememberAppState

@Composable
fun DirectionScreen() {
    val activity = LocalContext.current as ComponentActivity
    BackHandler {
        activity.finish()
    }
    Text("DirectionScreen")
}