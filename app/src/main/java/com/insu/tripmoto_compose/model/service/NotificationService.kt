package com.insu.tripmoto_compose.model.service

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import kotlin.random.Random
import com.insu.tripmoto_compose.R.drawable as AppIcon

class NotificationService(
    private val context: Context
) {
    private val notificationManager=context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification(title: String, content: String){
        val notification = NotificationCompat.Builder(context,"water_notification")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(AppIcon.google)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    ) = BitmapFactory.decodeResource(
        resources,
        resId
    )
}