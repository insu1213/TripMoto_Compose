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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.insu.tripmoto_compose.NavigationGraph
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.ui.theme.TripMotoTheme
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun BottomNavigation(navController: NavController) {
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
            .background(Color.White, shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            .shadow(elevation = 4.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun preview() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavigationGraph(navController = navController)
    }
}

