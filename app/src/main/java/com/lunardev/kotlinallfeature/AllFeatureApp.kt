package com.lunardev.kotlinallfeature

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lunardev.kotlinallfeature.core.theme.AppTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllFeatureApp(
    widthSizeClass: WindowWidthSizeClass
){
    AppTheme {
        val navController = rememberNavController()
        val navigationActions = AppNavigationActions(navController)
        val coroutineScope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: AppNavigationDestinations.HOME_ROUTE

        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

        Scaffold(
            bottomBar = {
                when (currentRoute) {
                    AppNavigationDestinations.HOME_ROUTE,
                    AppNavigationDestinations.MENU_ROUTE -> AppBottomNavigation(
                        currentRoute = currentRoute,
                        navigateToHome = navigationActions.navigateToHome,
                        navigateToMenu = navigationActions.navigateToMenu,
                        closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() }}
                    )
                }
            }
        ) {
            AppNavBuilder(
                navController = navController,
                currentRoute = currentRoute,
                coroutineScope = coroutineScope,
                isExpandedScreen = isExpandedScreen,
                sizeAwareDrawerState = sizeAwareDrawerState
            )
        }


    }
}

/**
 * Determine the drawer state to pass to the modal drawer.
 */
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}