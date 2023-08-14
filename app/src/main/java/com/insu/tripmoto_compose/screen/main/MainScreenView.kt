package com.insu.tripmoto_compose.screen.main

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.insu.tripmoto_compose.currentBackStackEntryAsState
import com.insu.tripmoto_compose.navGraph
import com.insu.tripmoto_compose.rememberAppState


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView() {
    val appState = rememberAppState()
    val isBottomBarVisible = remember { mutableStateOf(false) }

//    val navBackStackEntry = appState.navController.currentBackStackEntryAsState()
//    navBackStackEntry.value?.destination?.route

    appState.navController.currentBackStackEntryAsState().value?.destination?.route.let { route ->
        Log.d(TAG, "route: $route")
        isBottomBarVisible.value = when(route) {
            "map" ->  true
            "chat" -> true
            "wishList" -> true
            "direction" -> true
            "menu" -> true
            else -> false
        }
    }

    Scaffold(
        bottomBar = {
            if(isBottomBarVisible.value) {
                BottomNavigation(appState)
            }
        }
    ) {
        NavHost(appState.navController, startDestination = BottomNavItem.Map.screen_route) {
            navGraph(appState)
        }
    }
}

