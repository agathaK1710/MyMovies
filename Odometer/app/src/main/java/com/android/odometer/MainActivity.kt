package com.android.odometer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.service.autofill.TextValueSanitizer
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*

const val PERMISSION_REQUEST_CODE = 789
const val NOTIFICATION_ID = 123

class MainActivity : AppCompatActivity() {
    private lateinit var odometer: OdometerService
    private var bound = false

    private val connection = (object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val odometerBinder = service as OdometerService.OdometerBinder
            odometer = odometerBinder.getOdometer()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayDistance()
    }

    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(this, OdometerService.PERMISSION_STRING) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(this, OdometerService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(OdometerService.PERMISSION_STRING),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(this, OdometerService::class.java)
                    bindService(intent, connection, Context.BIND_AUTO_CREATE)
                } else {
                    val builder = NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_menu_compass)
                        .setContentText(resources.getString(R.string.permission_denied))
                        .setContentTitle(resources.getString(R.string.app_name))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(longArrayOf(1000, 1000))
                        .setAutoCancel(true)

                    val intent = Intent(this, MainActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    builder.setContentIntent(pendingIntent)
                    val notificationManager =
                        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(NOTIFICATION_ID, builder.build())
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(connection)
            bound = false
        }
    }

    fun displayDistance() {
        val textView = findViewById<TextView>(R.id.distance)
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                var distance = 0.0
                if (bound && odometer != null) {
                    distance = odometer.getDistance()
                }
                val stringDistance = String.format(
                    Locale.getDefault(),
                    "%1$,.2f metres", distance
                )
                textView.text = stringDistance
                handler.postDelayed(this, 1000)
            }
        })

    }
}