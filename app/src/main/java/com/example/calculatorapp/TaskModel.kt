package com.example.calculatorapp

import java.io.Serializable

class TaskModel(
    var name: String,
    var date: String,
    var time: String,
    val notificationId: Int
) : Serializable {
}
