package com.insu.tripmoto_compose.model.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import com.insu.tripmoto_compose.R

//class FCMService: FirebaseMessagingService() {
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        // 알림 채널 생성
//        createNotificationChannel()
//
//        // 푸시 알림 생성
//        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
//            .setContentTitle(remoteMessage.notification?.title)
//            .setContentText(remoteMessage.notification?.body)
//            .setSmallIcon(R.drawable.google)
//            .setAutoCancel(true)
//
//        // 알림 표시
//        val notificationManager = NotificationManagerCompat.from(this)
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        notificationManager.notify(0, notificationBuilder.build())
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = "channel_id"
//            val channelName = "My Channel"
//            val channelDescription = "Description of the channel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, channelName, importance).apply {
//                description = channelDescription
//            }
//
//            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//}