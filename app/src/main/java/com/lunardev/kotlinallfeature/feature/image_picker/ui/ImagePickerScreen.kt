package com.lunardev.kotlinallfeature.feature.image_picker.ui

import android.annotation.SuppressLint
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.lunardev.kotlinallfeature.R
import com.lunardev.kotlinallfeature.core.component.photoPickerLauncher


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerScreen(
    navigateBack: () -> Unit,
    isExpandedScreen: Boolean,
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.menu_content_image_picker),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
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
        }, content = { innerPadding ->
            val imagePickerViewModel: ImagePickerViewModel = viewModel(
                factory = ImagePickerViewModel.provideFactory()
            )
            val photoLauncher = photoPickerLauncher(
                maxSelectionCount = imagePickerViewModel.maxSelectionCount,
                onResult = { uris -> imagePickerViewModel.selectedImages.value = uris }
            )
            val cells = if (isExpandedScreen) 6 else 3


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ,
                verticalArrangement = if (imagePickerViewModel.selectedImages.value.isNotEmpty()) Top else Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                if(imagePickerViewModel.selectedImages.value.isEmpty())
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.picture_2659360),
                            contentDescription = "No Image",
                            modifier = Modifier.size(150.dp)
                        )
                        Text("No Image", color = Color.Gray)
                    }

                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        photoLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                    Text("Select Image")
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(cells),
                    modifier = Modifier
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                ) {
                    items(imagePickerViewModel.selectedImages.value) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(horizontal = 1.dp, vertical = 1.dp),
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                }
            }

        })
}

@Preview
@Composable
fun PostCardTopPreview() {
    ImagePickerScreen(
        isExpandedScreen = false,
        navigateBack = {

        },
    )
    Text(text = "Hello")
}