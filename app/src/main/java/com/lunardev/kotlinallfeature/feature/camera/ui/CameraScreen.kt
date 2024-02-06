package com.lunardev.kotlinallfeature.feature.camera.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.lunardev.kotlinallfeature.R
import com.lunardev.kotlinallfeature.core.component.appBar
import com.lunardev.kotlinallfeature.core.component.cameraLauncher
import com.lunardev.kotlinallfeature.core.component.permissionLauncher

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    navigateBack: () -> Unit,
    isExpandedScreen: Boolean,
){
    Scaffold(
        topBar = {
            appBar(navigateBack = navigateBack, title = stringResource(id = R.string.menu_content_camera))
        }, content = { innerPadding ->

            val context = LocalContext.current

            val cameraViewModel: CameraViewModel = viewModel(
                factory = CameraViewModel.provideFactory(context = context)
            )
            val cameraLaunch = cameraLauncher(
                onResult = { result -> if (result) cameraViewModel.capturedImageUri.value = cameraViewModel.uri }
            )
            val permissionLaunch = permissionLauncher(
                context = context,
                onResult = { result ->
                    if (result){
                        cameraLaunch.launch(cameraViewModel.uri)
                    } else {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                if (cameraViewModel.capturedImageUri.value.path?.isNotEmpty() == true) {
                    Image(
                        modifier = if(isExpandedScreen)
                            Modifier
                                .size(200.dp)
                                .aspectRatio(1f) else
                            Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                        painter = rememberAsyncImagePainter(cameraViewModel.capturedImageUri.value),
                        contentDescription = "Captured Image",
                        contentScale = ContentScale.FillWidth,
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.camera_6649088),
                            contentDescription = stringResource(id = R.string.no_image),
                            modifier = Modifier.size(150.dp)
                        )
                        Text(stringResource(id = R.string.no_image), color = Color.Gray)
                    }
                }


                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        if(cameraViewModel.permissionCheckResult == PackageManager.PERMISSION_GRANTED){
                            cameraLaunch.launch(cameraViewModel.uri)
                        } else {
                            permissionLaunch.launch(Manifest.permission.CAMERA)
                        }
                    }) {
                    Text(stringResource(id = R.string.open_camera))
                }
            }

        })
}

@Preview
@Composable
fun CameraScreenPreview() {
    CameraScreen(
        isExpandedScreen = false,
        navigateBack = {

        },
    )
}