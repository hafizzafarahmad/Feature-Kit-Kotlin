package com.lunardev.kotlinallfeature.feature.gps.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.lunardev.kotlinallfeature.core.model.LocationDetails
import java.util.Locale

class GPSViewModel(
   context: Context,
) :ViewModel() {

    var locationCallback: LocationCallback? = null
    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val geocoder = Geocoder(context, Locale.getDefault())

    val currentLocation: MutableState<LocationDetails> = mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))

    // Check Permission Camera
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)

    fun getAddress(): String {
        return try {
            val addresses = geocoder.getFromLocation(currentLocation.value.latitude, currentLocation.value.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val addressStringBuilder = StringBuilder()
                val address = addresses[0]
                for (i in 0..address.maxAddressLineIndex) {
                    addressStringBuilder.append(address.getAddressLine(i)).append("\n")
                }
                addressStringBuilder.toString()
            } else {
                "No address found"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    companion object {
        fun provideFactory(
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GPSViewModel(context = context) as T
            }
        }
    }
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
