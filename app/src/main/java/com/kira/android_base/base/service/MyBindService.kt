package com.kira.android_base.base.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class MyBindService : Service() {

    private val localBinder = LocalBinder()

    val randomNumber: Int get() = Random().nextInt(100)

    override fun onBind(intent: Intent): IBinder {
        return localBinder
    }

    inner class LocalBinder : Binder() {

        fun getService(): MyBindService {
            return this@MyBindService
        }
    }
}