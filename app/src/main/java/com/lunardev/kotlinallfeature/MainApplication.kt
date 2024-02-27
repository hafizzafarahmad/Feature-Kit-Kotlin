package com.lunardev.kotlinallfeature

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class MainApplication : Application() {
    companion object {
        const val APP_URI = "https://developer.android.com/jetnews"
    }

    override fun onCreate() {
        super.onCreate()

        val notificationChannel = NotificationChannel(
            "kotlin_all_feature",
            "Kotlin All Feature channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.description = "A notification channel for Kotlin All Feature"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}