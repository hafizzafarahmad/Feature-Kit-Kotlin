package com.lunardev.kotlinallfeature.feature.notification_fcm.ui

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.lunardev.kotlinallfeature.R


class NotificationFCMViewModel(
   context: Context,
) :ViewModel() {

    val tokenFcm: MutableState<String> = mutableStateOf("")

    // Check Permission Camera
    val permissionCheckResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
    } else {
        true
    }

    // Get Token FCM
    fun getTokenFcm(
        context: Context
    ) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(context, R.string.fetching_fcm_registration_token_filed, Toast.LENGTH_SHORT).show()
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            tokenFcm.value = token
            Log.d("FCM", "Refreshed token: $token")
        })
    }

    companion object {
        fun provideFactory(
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotificationFCMViewModel(context = context) as T
            }
        }
    }
}
