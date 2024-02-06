package com.lunardev.kotlinallfeature.feature.camera.ui

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lunardev.kotlinallfeature.BuildConfig
import com.lunardev.kotlinallfeature.core.utils.createImageFile
import java.util.Objects

class CameraViewModel(
   context: Context
) :ViewModel() {

    private val file = context.createImageFile()

    val uri: Uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "${BuildConfig.APPLICATION_ID}.provider", file
    )

    val capturedImageUri: MutableState<Uri> = mutableStateOf(Uri.EMPTY)

    // Check Permission Camera
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

    companion object {
        fun provideFactory(
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CameraViewModel(context = context) as T
            }
        }
    }
}
