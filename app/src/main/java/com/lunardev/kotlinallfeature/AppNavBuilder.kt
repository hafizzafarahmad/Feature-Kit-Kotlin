package com.lunardev.kotlinallfeature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.lunardev.kotlinallfeature.feature.camera.ui.CameraScreen
import com.lunardev.kotlinallfeature.feature.gps.ui.GPSScreen
import com.lunardev.kotlinallfeature.feature.image_picker.ui.ImagePickerScreen
import com.lunardev.kotlinallfeature.feature.menu.ui.MenuDestinations
import com.lunardev.kotlinallfeature.feature.menu.ui.MenuRoute
import com.lunardev.kotlinallfeature.feature.menu.ui.MenuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavBuilder(
    currentRoute: String,
    navController: NavHostController = rememberNavController(),
    isExpandedScreen: Boolean,
    coroutineScope: CoroutineScope,
    sizeAwareDrawerState: DrawerState
){
    NavHost(
        navController = navController,
        startDestination = AppNavigationDestinations.HOME_ROUTE,
    ) {
//        Bottom Navigation
        composable(
            route = AppNavigationDestinations.HOME_ROUTE,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "${MainApplication.APP_URI}/${AppNavigationDestinations.HOME_ROUTE}"
                }
            )
        ) { navBackStackEntry ->
            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.align(Alignment.Center)) {
                    Text(text = "Home")
                }
            }
        }
        composable(
            route = AppNavigationDestinations.MENU_ROUTE
        ) {
            val menuViewModel: MenuViewModel = viewModel(
                factory = MenuViewModel.provideFactory()
            )
            MenuRoute(
                navController = navController,
                menuViewModel = menuViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
            )
        }

//        Menu Navigation
        composable(
            route = MenuDestinations.ImagePicker.route
        ) {
            ImagePickerScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                isExpandedScreen = isExpandedScreen,
            )
        }
        composable(
            route = MenuDestinations.Camera.route
        ) {
            CameraScreen(navigateBack = { navController.navigateUp() }, isExpandedScreen = isExpandedScreen)
        }
        composable(
            route = MenuDestinations.GPS.route
        ) {
            GPSScreen(navigateBack = { navController.navigateUp() }, isExpandedScreen = isExpandedScreen)
        }
    }
}