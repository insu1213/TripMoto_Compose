package com.insu.tripmoto_compose

import android.content.Context
import android.content.res.Resources
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import android.Manifest
import android.os.Build
import android.provider.Settings.Secure.getString
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.screen.fore.travel_expenses.TravelExpensesScreen
import com.insu.tripmoto_compose.screen.fore.travel_members.TravelMembersScreen
import com.insu.tripmoto_compose.screen.fore.travel_place.TravelPlaceScreen
import com.insu.tripmoto_compose.screen.fore.travel_schedule.TravelScheduleScreen
import com.insu.tripmoto_compose.screen.fore.travel_title.TravelTitleScreen
import com.insu.tripmoto_compose.screen.login.LoginScreen
import com.insu.tripmoto_compose.screen.main.BottomNavItem
import com.insu.tripmoto_compose.screen.main.direction.DirectionScreen
import com.insu.tripmoto_compose.screen.main.MainScreen
import com.insu.tripmoto_compose.screen.main.chat.inner.ChatScreen
import com.insu.tripmoto_compose.screen.main.map.MapScreen
import com.insu.tripmoto_compose.screen.main.menu.MenuScreen
import com.insu.tripmoto_compose.screen.main.wishlist.WishListScreen
import com.insu.tripmoto_compose.screen.main.wishlist.edit.WishListEditScreen
import com.insu.tripmoto_compose.screen.member.MemberScreen
import com.insu.tripmoto_compose.screen.member.add.MemberAddScreen
import com.insu.tripmoto_compose.screen.sign_up.SignUpScreen
import com.insu.tripmoto_compose.screen.splash.SplashScreen
import com.insu.tripmoto_compose.screen.main.menu.travel_management.TravelManagementScreen
import com.insu.tripmoto_compose.screen.travel_option.TravelOptionScreen
import com.insu.tripmoto_compose.screen.trip_selection.TripSelectionScreen
import com.insu.tripmoto_compose.screen.settings.SettingsScreen
import com.insu.tripmoto_compose.ui.theme.TripMotoTheme
import io.grpc.internal.JsonUtil.getString
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
@ExperimentalMaterialApi
fun MyApp() {
    TripMotoTheme {

        val postNotificationPermission =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

        LaunchedEffect(key1 = true) {
            if (!postNotificationPermission.status.isGranted) {
                postNotificationPermission.launchPermissionRequest()
            }
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            val appState = rememberAppState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = "SplashScreen",
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    navGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        MyAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.navGraph(appState: MyAppState) {
    composable("SplashScreen") {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable("LoginScreen") {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable("SignUpScreen") {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable("TripSelectionScreen") {
        TripSelectionScreen(openAndPopUp = { route -> appState.navigate(route) })
    }
    composable("SettingsScreen") {
        SettingsScreen(openAndPopUp = { route -> appState.navigate(route) })
    }


    composable("TravelOptionScreen") {
        TravelOptionScreen(openAndPopUp = { route -> appState.navigate(route) })
    }
    composable("TravelTitleScreen") {
        TravelTitleScreen(openAndPopUp = { route -> appState.navigate(route) })
    }
    composable("TravelPlaceScreen") {
        TravelPlaceScreen(openAndPopUp = { route -> appState.navigate(route) })
    }
    composable("TravelScheduleScreen") {
        TravelScheduleScreen(openAndPopUp = { route -> appState.navigate(route) })
    }
    composable("TravelMembersScreen") {
        TravelMembersScreen(openAndPopUp = { route -> appState.navigate(route) })
    }
    composable("TravelExpensesScreen") {
        TravelExpensesScreen(openAndPopUp = { route -> appState.clearAndNavigate(route) })
    }

    composable("MainScreen") {
        MainScreen(openAndPopUp = { route -> appState.clearAndNavigate(route) })
    }
    composable(
        route = "TravelManagementScreen",
    ) {
        TravelManagementScreen(openAndPopUp = { route -> appState.navigate(route) })
    }
    composable(
        route = "MemberScreen",
    ) {
        MemberScreen(openScreen = { route -> appState.navigate(route) })
    }
    composable(
        route = "MemberAddScreen",
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(700)
            )
        },
    ) {
        MemberAddScreen(popUpScreen = { appState.popUp() })
    }

    composable(
        route = BottomNavItem.WishList.screen_route,
    ) {
        WishListScreen(openScreen = { route -> appState.navigate(route) })
    }
    composable(
        route = BottomNavItem.Direction.screen_route,
    ) {
        DirectionScreen()
    }
    composable(
        route = BottomNavItem.Map.screen_route,
    ) {
        MapScreen()
    }
    composable(
        route = BottomNavItem.Chat.screen_route,
    ) {
        ChatScreen()
    }
    composable(
        route = BottomNavItem.Menu.screen_route,
    ) {
        MenuScreen(openAndPopUp = { route -> appState.navigate(route) })
    }

    composable(
        route = "WishListEditScreen$WISHLIST_ID_ARG",
        arguments = listOf(navArgument(WISHLIST_ID) { defaultValue = WISHLIST_DEFAULT_ID })
    ) {
        WishListEditScreen(
            popUpScreen = { appState.popUp() },
            wishListId = it.arguments?.getString(WISHLIST_ID) ?: WISHLIST_DEFAULT_ID
        )
    }
}



@Composable
@ExperimentalMaterialApi
public fun NavController.currentBackStackEntryAsState(): State<NavBackStackEntry?> =
    currentBackStackEntryFlow.collectAsState(null)