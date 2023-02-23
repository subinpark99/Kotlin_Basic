package com.example.basic.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TorchService:Service() {

    private val torch:Torch by lazy {
        Torch(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            "on"-> torch.flashOn()
            "off"-> torch.flashOff()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
       return null
    }
}