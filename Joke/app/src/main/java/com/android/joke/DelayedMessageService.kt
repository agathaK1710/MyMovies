package com.android.joke

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.graphics.Color
import android.os.Build


class DelayedMessageService : IntentService("DelayedMessageService") {

    override fun onHandleIntent(intent: Intent?) {
        synchronized(this){
            try {
               Thread.sleep(1000)
            } catch (e: InterruptedException){
                e.printStackTrace()
            }
            val text = intent?.getStringExtra(EXTRA_MESSAGE)

            if (text != null) {

               val builder = NotificationCompat.Builder(this)
                   .setSmallIcon(android.R.drawable.sym_def_app_icon)
                   .setContentTitle(getString(R.string.question))
                   .setContentText(text)
                   .setPriority(NotificationCompat.PRIORITY_HIGH)
                   .setVibrate(longArrayOf(0, 1000))
                   .setAutoCancel(true)

                val intent = Intent(this, MainActivity:: class.java)
                val pendingIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT)

                builder.setContentIntent(pendingIntent)
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(NOTIFICATION_ID, builder.build())
            }
        }
    }


    companion object {
        const val EXTRA_MESSAGE = "message"
        const val NOTIFICATION_ID = 1710
        const val CHANNEL_ID = "5487"
    }
}