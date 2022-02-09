package com.android.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailActivity : AppCompatActivity() {
    private lateinit var detailFragment: WorkoutDetailFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailFragment = supportFragmentManager.findFragmentById(R.id.detail_frag) as WorkoutDetailFragment
        detailFragment.setWorkout(intent.extras?.getInt(EXTRA_WORKOUT_ID)?.toLong()!!)
    }
    companion object{
        const val EXTRA_WORKOUT_ID = "workout_detail"
    }
}