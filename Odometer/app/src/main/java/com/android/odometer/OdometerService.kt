package com.android.odometer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder
import androidx.core.content.ContextCompat

class OdometerService : Service() {
    private val binder: IBinder = OdometerBinder()
    private lateinit var listener: LocationListener
    private lateinit var locManager: LocationManager
    private var distanceInMetres = 0.0
    private var lastLocation: Location? = null

    override fun onCreate() {
        super.onCreate()
        listener = (object : LocationListener {
            override fun onLocationChanged(location: Location) {
               if(lastLocation == null){
                   lastLocation = location
               }
                distanceInMetres += location.distanceTo(lastLocation)
                lastLocation = location
            }
        })

        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val provider = locManager.getBestProvider(Criteria(), true)
            if (provider != null){
                locManager.requestLocationUpdates(provider, 1000, 1.toFloat(), listener)
            }
        }


    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        if(ContextCompat.checkSelfPermission(this, PERMISSION_STRING) ==
                PackageManager.PERMISSION_GRANTED) {
            locManager.removeUpdates(listener)
        }
    }

    fun getDistance(): Double {
        return distanceInMetres
    }

    inner class OdometerBinder : Binder() {
        fun getOdometer(): OdometerService {
            return this@OdometerService
        }
    }

    companion object{
        const val PERMISSION_STRING = android.Manifest.permission.ACCESS_COARSE_LOCATION
    }
}