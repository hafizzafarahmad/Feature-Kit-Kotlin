package com.lunardev.kotlinallfeature.feature.notification_fcm.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lunardev.kotlinallfeature.R
import com.lunardev.kotlinallfeature.core.component.appBar
import com.lunardev.kotlinallfeature.core.component.permissionLauncher
import com.lunardev.kotlinallfeature.core.utils.LocalNotificationService

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationFCMScreen(
    navigateBack: () -> Unit,
    isExpandedScreen: Boolean,
){
    Scaffold(
        topBar = {
            appBar(navigateBack = navigateBack, title = stringResource(id = R.string.menu_content_firebase_notification))
        }, content = { innerPadding ->
            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current

            val localNotificationService = LocalNotificationService(context)

            val notificationFCMViewModel: NotificationFCMViewModel = viewModel(
                factory = NotificationFCMViewModel.provideFactory(context = context)
            )


            val permissionLaunch = permissionLauncher(
                context = context,
                onResult = { result ->
                    if (result){
                        Toast.makeText(context, R.string.permission_granted, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                    }
                }
            )

            LaunchedEffect(permissionLaunch) {
                if(notificationFCMViewModel.permissionCheckResult == PackageManager.PERMISSION_DENIED){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLaunch.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
                notificationFCMViewModel.getTokenFcm(context)
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)

            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "Token FCM",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = notificationFCMViewModel.tokenFcm.value,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        modifier = Modifier.padding(top = 20.dp),
                        onClick = {
                            localNotificationService.showBasicNotification(
                                title = "Notification title",
                                desc = "Notification text Description"
                            )
                        }) {
                        Text(stringResource(id = R.string.show_notification))
                    }

                    Button(
                        modifier = Modifier.padding(top = 20.dp),
                        onClick = {

                        }) {
                        Text(stringResource(id = R.string.refresh))
                    }
                }
            }


        })
}

@Preview
@Composable
fun Preview() {
    NotificationFCMScreen(
        isExpandedScreen = false,
        navigateBack = {

        },
    )
}