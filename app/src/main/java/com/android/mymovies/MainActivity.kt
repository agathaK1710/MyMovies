package com.android.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.mymovies.utils.JSONUtils
import com.android.mymovies.utils.NetworkUtils
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonObject = NetworkUtils.getJSONObject(NetworkUtils.POPULARITY, 1)
        val movies = JSONUtils.getMovieFromJSON(jsonObject)

        for (i in movies){
            Log.i("MainActivity", i.title + "\n")
        }

    }
}