package com.example.calculatorapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel1"
        const val TITLE_EXTRA = "titleExtra"
        const val MESSAGE_EXTRA = "messageExtra"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_edit)
            .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
            .setContentText(intent.getStringExtra(MESSAGE_EXTRA))
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}

fun createNotificationChannel(context: Context) {
    val name = "Notification Channel"
    val description = "A Description of channel"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(NotificationReceiver.CHANNEL_ID, name, importance)
        channel.description = description

        context?.let {
            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}




