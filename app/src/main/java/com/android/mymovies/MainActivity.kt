package com.android.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.utils.JSONUtils
import com.android.mymovies.utils.NetworkUtils
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var rV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonObject = NetworkUtils.getJSONObject(NetworkUtils.POPULARITY, 3)
        val jsonArray = JSONUtils.getMovieFromJSON(jsonObject)
        val movies: ArrayList<Movie> = arrayListOf()
        for(i in jsonArray){
            movies.add(i)
        }
        rV = findViewById(R.id.recycler)
        rV.adapter = MovieAdapter(movies)
        rV.layoutManager = GridLayoutManager(this, 2)


    }
}