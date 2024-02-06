package com.lunardev.kotlinallfeature.core.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun appBar(
    navigateBack: () -> Unit,
    title: String,
    titleOnly: Boolean = false
){
    CenterAlignedTopAppBar(
        title =  {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        navigationIcon = {
            if(!titleOnly)
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back Icon",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
        )
    )
}