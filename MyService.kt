package com.appb

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService : Service() {

    private val binder = MyBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class MyBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    fun getData(): String {
        return "Data from App B"
    }
}
