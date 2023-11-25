package com.example.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.aidl.IAdditionInterface

class RemoteService : Service() {

    private val binder = object : IAdditionInterface.Stub() {
        override fun add(x: Int, y: Int): Int {
            return x + y
        }

        override fun subtract(x: Int, y: Int): Int {
            return x - y
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}