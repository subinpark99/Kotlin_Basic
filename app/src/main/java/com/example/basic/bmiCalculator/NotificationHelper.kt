package com.example.basic.bmiCalculator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.basic.R


class NotificationHelper(context: Context?):ContextWrapper(context) {

    private val CHANNEL_ID="channelId"
    private val CHANNEL_NM="channelName"

    init {
        createChannel()
    }

    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NM, importance)
            channel.enableLights(true)
            channel.lightColor= Color.GREEN
            channel.enableVibration(true)

            getManager().createNotificationChannel(channel)
        }
    }
    fun getManager():NotificationManager{
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun getChannelNotification(title:String,message: String):NotificationCompat.Builder {

        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.icon_smile)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}