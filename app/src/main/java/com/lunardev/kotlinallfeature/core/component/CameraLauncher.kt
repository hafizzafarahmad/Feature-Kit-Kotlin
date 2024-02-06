package com.lunardev.kotlinallfeature.core.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun cameraLauncher(
    onResult: (Boolean) -> Unit
) =  rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        result -> onResult((result))
}