package com.insu.tripmoto_compose.screen.main

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.insu.tripmoto_compose.NavigationGraph
import com.insu.tripmoto_compose.rememberAppState
import com.insu.tripmoto_compose.ui.theme.TripMotoTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView() {
    val appState = rememberAppState()
    Scaffold(
        bottomBar = { BottomNavigation(appState) }
    ) {
        NavigationGraph(appState)
    }
}

