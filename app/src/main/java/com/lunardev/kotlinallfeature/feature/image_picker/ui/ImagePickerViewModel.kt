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

    val buttonText: String
        get() = if (maxSelectionCount > 1) {
            "Select up to $maxSelectionCount photos"
        } else {
            "Select a photo"
        }

//    fun launchPhotoPicker() {
//        if (maxSelectionCount > 1) {
//            multiplePhotoPickerLauncher.launch(
//                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//            )
//        } else {
//            singlePhotoPickerLauncher.launch(
//                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//            )
//        }
//    }

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

