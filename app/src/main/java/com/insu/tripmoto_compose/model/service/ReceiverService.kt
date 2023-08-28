package com.insu.tripmoto_compose.model.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.insu.tripmoto_compose.R

class ReceiverService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        receiveEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast")
    private fun receiveEvent() {
        // 알림 채널 ID와 이름 설정
        val CHANNEL_ID = "my_channel_id"
        val CHANNEL_NAME = "백그라운드에서 실행"
        val NOTIFICATION_RECEIVE_MODE = 1

        // 알림 매니저 가져오기
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 알림 채널 생성 (API 26 이상에서 필요)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.
                IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 빌더 생성
        val notificationBuilder = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.google)
            .setContentTitle("채팅 수신 중")
            .setContentText("백그라운드에서 채팅 수신 대기중입니다.")
            .setPriority(Notification.PRIORITY_DEFAULT)

        startForeground(NOTIFICATION_RECEIVE_MODE, notificationBuilder.build())
    }
}