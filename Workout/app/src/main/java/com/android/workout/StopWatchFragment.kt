package com.android.workout

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class StopWatchFragment : Fragment() {
    private lateinit var start: Button
    private lateinit var stop: Button
    private lateinit var reset: Button
    private lateinit var time: TextView

    private var seconds = 0
    private var running  = false
    private var wasRunning = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = inflater.inflate(R.layout.fragment_stop_watch, container, false)
        start = layout?.findViewById(R.id.start_button)!!
        stop = layout.findViewById(R.id.stop_button)!!
        reset = layout.findViewById(R.id.reset_button)!!
        runTimer(layout)
        return  layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start.setOnClickListener {
            running = true
            wasRunning = running
        }

        stop.setOnClickListener {
            running = false
            wasRunning = running
        }

        reset.setOnClickListener {
            running = false
            wasRunning = running
            seconds = 0
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onResume() {
        super.onResume()
        running = wasRunning
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
    }

    fun runTimer(view: View) {
        time = view.findViewById(R.id.time_view)!!
        val handler = Handler()
        handler.post(object: Runnable{
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                val stringTime = String.format(
                    "%d:%02d:%02d",
                    hours, minutes, secs
                )
                time.text = stringTime
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}