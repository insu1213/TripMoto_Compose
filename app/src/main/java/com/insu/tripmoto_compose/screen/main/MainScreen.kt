package com.insu.tripmoto_compose.screen.main

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.insu.tripmoto_compose.rememberAppState

@Composable
fun MainScreen(
    openAndPopUp: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    MainScreenView()
}