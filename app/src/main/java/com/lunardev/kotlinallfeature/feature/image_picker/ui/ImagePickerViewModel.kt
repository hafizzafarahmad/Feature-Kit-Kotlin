package com.lunardev.kotlinallfeature.feature.image_picker.ui

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImagePickerViewModel : ViewModel() {

    val selectedImages: MutableState<List<Uri?>> = mutableStateOf(emptyList())

    // Add maxSelectionCount variable with its getter and setter
    var maxSelectionCount: Int = 5


    companion object {
        fun provideFactory(
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ImagePickerViewModel() as T
            }
        }
    }
}

