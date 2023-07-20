package com.insu.tripmoto_compose.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.insu.tripmoto_compose.MyAppState
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.ui.theme.TripMotoTheme
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun BottomNavigation(appState: MyAppState) {
    val items = listOf(
        BottomNavItem.WishList,
        BottomNavItem.Direction,
        BottomNavItem.Map,
        BottomNavItem.Chat,
        BottomNavItem.Menu
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(AppColor.white),
        contentColor = Color.Black,
        modifier = Modifier
            .padding(bottom = 12.dp, start = 8.dp, end = 8.dp)
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(15.dp)
                shadowElevation = 20f
            },
        elevation = 20.dp
    ) {
        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier,
                icon = {
                    Icon(
                        painterResource(item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                //label = { Text(text = item.title, fontSize = 9.sp) },
                selectedContentColor = colorResource(AppColor.primary_800),
                unselectedContentColor = colorResource(AppColor.gray_4),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    appState.navController.navigate(item.screen_route) {
                        appState.navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
