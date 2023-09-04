package com.insu.tripmoto_compose.screen.main.direction

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.rememberAppState

@Composable
fun DirectionScreen() {

    BackOnPressed()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("DirectionScreen")
    }


}