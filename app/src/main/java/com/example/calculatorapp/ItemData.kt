package com.example.calculatorapp

import android.app.PendingIntent
import android.content.Intent
import java.io.Serializable
import java.util.Date
import java.util.Timer

class ItemData(
    var name: String,
    var date: String,
    var time: String,
    val notificationId: Int
): Serializable {
}
