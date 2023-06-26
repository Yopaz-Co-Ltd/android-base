package com.kira.android_base.base.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.kira.android_base.R
import com.kira.android_base.base.supports.utils.Enums
import com.kira.android_base.main.MainActivity

class MyForegroundService : LifecycleService() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        private const val NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME"
        private const val NOTIFICATION_ID = 1

        private const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

        private const val PENDING_INTENT_REQUEST_CODE = 0

        val isTrackingLiveData = MutableLiveData(false)
    }

    private var isFirstRun = true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                Enums.CommandService.ACTION_START_OR_RESUME_SERVICE.value -> {
                    if (!isFirstRun) return@let
                    isFirstRun = false
                    startForegroundService()
                }

                Enums.CommandService.ACTION_PAUSE_SERVICE.value -> {
                    pauseService()
                }

                Enums.CommandService.ACTION_STOP_SERVICE.value -> {
                    killService()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        isTrackingLiveData.postValue(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            PENDING_INTENT_REQUEST_CODE,
            Intent(this, MainActivity::class.java).apply {
                action = ACTION_SHOW_TRACKING_FRAGMENT
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("foreground Service is running")
            .setContentText("Running")
            .setContentIntent(pendingIntent)

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun pauseService() {
        isTrackingLiveData.postValue(false)
    }

    private fun killService() {
        isTrackingLiveData.postValue(false)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}