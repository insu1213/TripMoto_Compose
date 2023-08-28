package com.insu.tripmoto_compose.model.service

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import com.insu.tripmoto_compose.R

class FCMService: FirebaseMessagingService() {
//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//
//        val title = remoteMessage("") {}.notification?.title
//        val body = remoteMessage("") {}.notification?.body
//
//        if(title != null && body != null) {
//            //showBackgroundNotification(title, body)
//        }
//    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

//    private fun showBackgroundNotification(title: String, body: String) {
//        val notificationManager = NotificationManagerCompat.from(this)
//
//        // 알림 생성 및 설정
//        val notification = NotificationCompat.Builder(this, "channel_id")
//            .setContentTitle(title)
//            .setContentText(body)
//            .setSmallIcon(R.drawable.google)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .build()
//
//        // 알림 표시
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        notificationManager.notify(1, notification)
//    }
}