package com.lunardev.kotlinallfeature.feature.gps.ui

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FingerprintViewModel(
   context: Context,
) :ViewModel() {

    private val biometricManager = BiometricManager.from(context)
    val canAuthenticateWithBiometrics = when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
        BiometricManager.BIOMETRIC_SUCCESS -> true
        else -> {
            Log.e("TAG", "Device does not support strong biometric authentication")
            false
        }
    }

    companion object {
        fun provideFactory(
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FingerprintViewModel(context = context) as T
            }
        }
    }
}
