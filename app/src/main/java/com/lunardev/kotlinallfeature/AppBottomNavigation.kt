package com.lunardev.kotlinallfeature

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun AppBottomNavigation(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToMenu: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {

    NavigationBar(modifier) {
        //getting the list of bottom navigation items for our data class
        NavigationBarItem(
            selected = currentRoute == AppNavigationDestinations.HOME_ROUTE,
            label = { Text(stringResource(id = R.string.home_title)) },
            icon = { Icon(Icons.Filled.Home, null) },
            onClick = {  navigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationBarItem(
            selected = currentRoute == AppNavigationDestinations.MENU_ROUTE,
            label = { Text(stringResource(id = R.string.menu_title)) },
            icon = { Icon(Icons.Filled.Menu, null) },
            onClick = {  navigateToMenu(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

/**
 * Destinations used in the [This App].
 */
object AppNavigationDestinations {
    const val HOME_ROUTE = "home"
    const val MENU_ROUTE = "menu"
}

/**
 * Models the navigation actions in the app.
 */
class AppNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(AppNavigationDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToMenu: () -> Unit = {
        navController.navigate(AppNavigationDestinations.MENU_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}