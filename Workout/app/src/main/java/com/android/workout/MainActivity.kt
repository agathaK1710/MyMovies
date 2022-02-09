package com.android.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE

class MainActivity : AppCompatActivity(), WorkoutListFragment.Listener {
    private  var fragmentContainer: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainer = findViewById(R.id.fragment_container)
    }

    override fun itemClicked(id: Long) {   
        if(fragmentContainer != null){
            val details = WorkoutDetailFragment()
            val fr = supportFragmentManager.beginTransaction()
            details.setWorkout(id)
            fr.replace(R.id.fragment_container, details)
            fr.setTransition(TRANSIT_FRAGMENT_FADE)
            fr.addToBackStack(null)
            fr.commit()

        } else {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, id.toInt())
            startActivity(intent)
        }
    }




}