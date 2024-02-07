package com.lunardev.kotlinallfeature.feature.gps.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.lunardev.kotlinallfeature.R
import com.lunardev.kotlinallfeature.core.component.appBar
import com.lunardev.kotlinallfeature.core.component.permissionLauncher
import com.lunardev.kotlinallfeature.core.model.LocationDetails

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GPSScreen(
    navigateBack: () -> Unit,
    isExpandedScreen: Boolean,
){
    Scaffold(
        topBar = {
            appBar(navigateBack = navigateBack, title = stringResource(id = R.string.menu_content_gps))
        }, content = { innerPadding ->
            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current

            val gpsViewModel: GPSViewModel = viewModel(
                factory = GPSViewModel.provideFactory(context = context)
            )

            val permissionLaunch = permissionLauncher(
                context = context,
                onResult = { result ->
                    if (result){
                        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            gpsViewModel.locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    for (lo in p0.locations) {
                        // Update UI with location data
                        gpsViewModel.currentLocation.value = LocationDetails(lo.latitude, lo.longitude)
                    }
                }
            }

            LaunchedEffect(permissionLaunch) {
                if(gpsViewModel.permissionCheckResult == PackageManager.PERMISSION_DENIED){
                    permissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            DisposableEffect(Unit) {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_RESUME -> {
                            startLocationUpdates(
                               gpsViewModel
                            )
                        }
                        Lifecycle.Event.ON_PAUSE -> {
                            gpsViewModel.locationCallback?.let { gpsViewModel.fusedLocationClient.removeLocationUpdates(it) }
                        }
                        else -> { }
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)

                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Latitude : " + gpsViewModel.currentLocation.value.latitude)
                Text(text = "Longitude : " + gpsViewModel.currentLocation.value.longitude)
                Text(
                    text = "Address : " + gpsViewModel.getAddress(),
                    textAlign = TextAlign.Center
                )
                
                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        if(gpsViewModel.permissionCheckResult == PackageManager.PERMISSION_GRANTED){
                            startLocationUpdates(gpsViewModel)
                        } else {
                            permissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }) {
                    Text(stringResource(id = R.string.refresh))
                }
            }

        })
}

@SuppressLint("MissingPermission")
fun startLocationUpdates(
        gpsViewModel: GPSViewModel
    ) {
    gpsViewModel.locationCallback.let {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        if (it != null) {
            gpsViewModel.fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }
}

@Preview
@Composable
fun GPSPreview() {
    GPSScreen(
        isExpandedScreen = false,
        navigateBack = {

        },
    )
}