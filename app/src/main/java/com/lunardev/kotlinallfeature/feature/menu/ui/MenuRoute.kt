package com.lunardev.kotlinallfeature.feature.menu.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MenuRoute(
    navController: NavHostController = rememberNavController(),
    menuViewModel: MenuViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val tabContent = rememberTabContent(
        menuViewModel = menuViewModel,
        isExpandedScreen = isExpandedScreen,
        navController = navController
    )

    val (currentSection, updateSection) = rememberSaveable {
        mutableStateOf(tabContent.first().section)
    }

    MenuScreen(
        tabContent = tabContent,
        currentSection = currentSection,
        isExpandedScreen = isExpandedScreen,
        onTabChange = updateSection,
        openDrawer = openDrawer,
        snackbarHostState = snackbarHostState,
    )
}