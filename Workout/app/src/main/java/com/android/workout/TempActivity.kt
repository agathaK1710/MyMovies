package com.android.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction

class TempActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        if(savedInstanceState == null){
            val fr = supportFragmentManager.beginTransaction()
            fr.add(R.id.stopwatch_container, StopWatchFragment())
            fr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fr.addToBackStack(null)
            fr.commit()
        }
    }
}