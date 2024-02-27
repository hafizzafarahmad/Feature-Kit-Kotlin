package com.lunardev.kotlinallfeature.core.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.lunardev.kotlinallfeature.R
import kotlin.random.Random

class LocalNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification(
        title: String? = "title",
        desc: String? = "description"
    ) {
        val notification = NotificationCompat.Builder(context, "kotlin_all_feature")
            .setContentTitle(title)
            .setContentText(desc)
            .setSmallIcon(R.drawable.fingerprint_2313448)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}