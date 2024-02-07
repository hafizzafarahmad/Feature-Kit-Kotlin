package com.lunardev.kotlinallfeature.feature.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lunardev.kotlinallfeature.R
import com.lunardev.kotlinallfeature.core.component.appBar
import com.lunardev.kotlinallfeature.feature.menu.ui.component.ItemMenuContent

/**
 * TabContent for a single tab of the screen.
 *
 * This is intended to encapsulate a tab & it's content as a single object. It was added to avoid
 * passing several parameters per-tab from the stateful composable to the composable that displays
 * the current tab.
 *
 * @param section the tab that this content is for
 * @param section content of the tab, a composable that describes the content
 */
class TabContent(val section: Sections, val content: @Composable () -> Unit)

/**
 * Stateless interest screen displays the tabs specified in [tabContent] adapting the UI to
 * different screen sizes.
 *
 * @param tabContent (slot) the tabs and their content to display on this screen, must be a
 * non-empty list, tabs are displayed in the order specified by this list
 * @param currentSection (state) the current tab to display, must be in [tabContent]
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param onTabChange (event) request a change in [currentSection] to another tab from [tabContent]
 * @param openDrawer (event) request opening the app drawer
 * @param snackbarHostState (state) the state for the screen's [Scaffold]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    tabContent: List<TabContent>,
    currentSection: Sections,
    isExpandedScreen: Boolean,
    onTabChange: (Sections) -> Unit,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { appBar(navigateBack = {  }, title = stringResource(id = R.string.menu_title), titleOnly = true) }
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        MenuScreenContent(
            currentSection, isExpandedScreen,
            onTabChange, tabContent, screenModifier,
        )
    }
}

/**
 * Displays a tab row with [currentSection] selected and the body of the corresponding [tabContent].
 *
 * @param currentSection (state) the tab that is currently selected
 * @param isExpandedScreen (state) whether or not the screen is expanded
 * @param updateSection (event) request a change in tab selection
 * @param tabContent (slot) tabs and their content to display, must be a non-empty list, tabs are
 * displayed in the order of this list
 */
@Composable
private fun MenuScreenContent(
    currentSection: Sections,
    isExpandedScreen: Boolean,
    updateSection: (Sections) -> Unit,
    tabContent: List<TabContent>,
    modifier: Modifier = Modifier
) {
    val selectedTabIndex = tabContent.indexOfFirst { it.section == currentSection }
    Column(modifier) {
        MenuTabRow(selectedTabIndex, updateSection, tabContent, isExpandedScreen)
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
        Box(modifier = Modifier.weight(1f)) {
            // display the current tab content which is a @Composable () -> Unit
            tabContent[selectedTabIndex].content()
        }
    }
}

/**
 * TabRow for the InterestsScreen
 */
@Composable
private fun MenuTabRow(
    selectedTabIndex: Int,
    updateSection: (Sections) -> Unit,
    tabContent: List<TabContent>,
    isExpandedScreen: Boolean
) {
    when (isExpandedScreen) {
        false -> {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                MenuTabRowContent(selectedTabIndex, updateSection, tabContent)
            }
        }
        true -> {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = MaterialTheme.colorScheme.primary,
                edgePadding = 0.dp
            ) {
                MenuTabRowContent(
                    selectedTabIndex = selectedTabIndex,
                    updateSection = updateSection,
                    tabContent = tabContent,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun MenuTabRowContent(
    selectedTabIndex: Int,
    updateSection: (Sections) -> Unit,
    tabContent: List<TabContent>,
    modifier: Modifier = Modifier
) {
    tabContent.forEachIndexed { index, content ->
        val colorText = if (selectedTabIndex == index) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        }
        Tab(
            selected = selectedTabIndex == index,
            onClick = { updateSection(content.section) },
            modifier = Modifier.heightIn(min = 48.dp)
        ) {
            Text(
                text = stringResource(id = content.section.titleResId),
                color = colorText,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.paddingFromBaseline(top = 20.dp)
            )
        }
    }
}


/**
 * Remembers the content for each tab on the Interests screen
 * gathering application data from [MenuViewModel]
 */
@Composable
fun rememberTabContent(
    menuViewModel: MenuViewModel,
    isExpandedScreen: Boolean,
    navController: NavHostController = rememberNavController(),
): List<TabContent> {
    // UiState of the InterestsScreen
    val uiState by menuViewModel.uiState.collectAsStateWithLifecycle()

    // Describe the screen sections here since each section needs 2 states and 1 event.
    // Pass them to the stateless InterestsScreen using a tabContent.
    val localSection = TabContent(Sections.Local) {
        Box(modifier = Modifier.fillMaxSize()) {
            val cells = if (isExpandedScreen) 6 else 3

            LazyVerticalGrid(
                columns = GridCells.Fixed(cells),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(Menus.entries.filter { it.section == Sections.Local }) { menu ->
                    ItemMenuContent(
                        menu = menu,
                        onClick = {
                            when (menu.title) {
                                Menus.ImagePicker.title -> navController.navigate(MenuDestinations.ImagePicker.route)
                                Menus.Camera.title -> navController.navigate(MenuDestinations.Camera.route)
                                Menus.GPS.title -> navController.navigate(MenuDestinations.GPS.route)
                            }
                        }
                    )
                }
            }
        }
    }

    val apiSection = TabContent(Sections.API) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.align(Alignment.Center)) {
                Text(text = "API")
            }
        }
    }

    val externalSection = TabContent(Sections.External) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.align(Alignment.Center)) {
                Text(text = "External")
            }
        }
    }

    return listOf(localSection, apiSection, externalSection)
}



