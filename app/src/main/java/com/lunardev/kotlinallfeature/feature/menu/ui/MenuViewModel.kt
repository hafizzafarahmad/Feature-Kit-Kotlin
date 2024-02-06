package com.lunardev.kotlinallfeature.feature.menu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MenuUiState(
    val topics: List<String> = emptyList(),
    val people: List<String> = emptyList(),
    val publications: List<String> = emptyList(),
    val loading: Boolean = false,
)

class MenuViewModel() : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(MenuUiState(loading = true))
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    /**
     * Factory for InterestsViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MenuViewModel() as T
            }
        }
    }
}

