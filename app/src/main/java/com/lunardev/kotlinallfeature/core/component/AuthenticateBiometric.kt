package com.lunardev.kotlinallfeature.core.component

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

fun authenticateWithBiometric(
    context: Context,
    onResult: (String) -> Unit
) {
    val executor = context.mainExecutor
    val biometricPrompt = BiometricPrompt(
        context as FragmentActivity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                //TODO handle authentication success, proceed to HomeScreen
                Log.d("TAG", "Authentication successful!!!")
                onResult("Authentication success")
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Log.e("TAG", "onAuthenticationError")
                //TODO Handle authentication errors.
                onResult("Authentication Error")
            }

            override fun onAuthenticationFailed() {
                Log.e("TAG", "onAuthenticationFailed")
                onResult("Authentication Failed")
                //TODO Handle authentication failures.
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Authentication")
        .setDescription("Place your finger the sensor or look at the front camera to authenticate.")
        .setNegativeButtonText("Cancel")
        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        .build()

    biometricPrompt.authenticate(promptInfo)
}