package com.lunardev.kotlinallfeature.core.component

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun permissionLauncher(
    context: Context,
    onResult: (Boolean) -> Unit
) = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
) { result ->
    onResult((result))
}