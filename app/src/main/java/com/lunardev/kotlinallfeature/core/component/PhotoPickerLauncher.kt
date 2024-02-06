package com.lunardev.kotlinallfeature.core.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun photoPickerLauncher(
    maxSelectionCount: Int,
    onResult: (List<Uri?>) -> Unit
) = if(maxSelectionCount == 1)
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onResult(listOf(uri)) }
    ) else
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = if (maxSelectionCount > 1) {
            maxSelectionCount
        } else {
            2
        }),
        onResult = onResult
    )