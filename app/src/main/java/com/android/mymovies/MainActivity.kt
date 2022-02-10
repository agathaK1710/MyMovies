package com.android.mymovies

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.data.Movie
import com.android.mymovies.utils.JSONUtils
import com.android.mymovies.utils.NetworkUtils

class MainActivity : AppCompatActivity() {
    private lateinit var rV: RecyclerView
    private lateinit var switch: SwitchCompat
    private lateinit var adapter: MovieAdapter
    private lateinit var popularity: TextView
    private lateinit var top_rated: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movies: ArrayList<Movie> = arrayListOf()
        popularity = findViewById(R.id.popularity)
        top_rated = findViewById(R.id.top_rated)
        switch = findViewById(R.id.switchSort)
        rV = findViewById(R.id.recycler)
        adapter = MovieAdapter(movies)
        adapter.setPosterClickListener(object : MovieAdapter.PosterClickListener {
            override fun onPosterClick(position: Int) {
                Toast.makeText(this@MainActivity,
                    position.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        adapter.setReachEndListener(object : MovieAdapter.ReachEndListener {
            override fun onReachEnd() {
                Toast.makeText(this@MainActivity,
                    "The end of list", Toast.LENGTH_SHORT).show()
            }
        })
        rV.adapter = adapter
        rV.layoutManager = GridLayoutManager(this, 2)
        switch.isChecked = true
        switch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                setMethodOfSort(isChecked)
            }
        })
        popularity.setOnClickListener {
            setMethodOfSort(false)
            switch.isChecked = false
        }
        top_rated.setOnClickListener {
            setMethodOfSort(true)
            switch.isChecked = true
        }
        switch.isChecked = false

    }

    fun setMethodOfSort(isChecked: Boolean) {
        val methodOfSort = if (isChecked) {
            popularity.setTextColor(resources.getColor(R.color.white))
            top_rated.setTextColor(resources.getColor(R.color.rose))
            NetworkUtils.TOP_RATED
        } else {
            popularity.setTextColor(resources.getColor(R.color.rose))
            top_rated.setTextColor(resources.getColor(R.color.white))
            NetworkUtils.POPULARITY
        }
        val jsonObject = NetworkUtils.getJSONObject(methodOfSort, 1)
        val jsonArray = JSONUtils.getMovieFromJSON(jsonObject)

        adapter.movies = jsonArray
        adapter.notifyDataSetChanged()
    }
}