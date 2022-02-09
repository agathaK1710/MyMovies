package com.android.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE

class WorkoutDetailFragment : Fragment() {
    private var workoutId: Long? = null
    private lateinit var title: TextView
    private lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fr = childFragmentManager.beginTransaction()
            fr.add(R.id.stopwatch_container, StopWatchFragment())
            fr.setTransition(TRANSIT_FRAGMENT_FADE)
            fr.addToBackStack(null)
            fr.commit()
        } else {
            workoutId = savedInstanceState.getLong("PUT")
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        val workout = Workout.workouts[workoutId?.toInt()!!]
        title = view?.findViewById(R.id.textTitle)!!
        description = view?.findViewById(R.id.textDescription)!!
        title.text = workout.name
        description.text = workout.description
    }

    override fun onSaveInstanceState(outState: Bundle) {
        workoutId?.let { outState.putLong("PUT", it) }

    }

    fun setWorkout(id: Long) {
        workoutId = id
    }
}
