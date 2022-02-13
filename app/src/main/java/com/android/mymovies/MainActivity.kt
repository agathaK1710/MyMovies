package com.android.mymovies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.data.MainViewModel
import com.android.mymovies.data.Movie
import com.android.mymovies.utils.JSONUtils
import com.android.mymovies.utils.NetworkUtils

class MainActivity : AppCompatActivity() {
    private lateinit var rV: RecyclerView
    private lateinit var switch: SwitchCompat
    private lateinit var adapter: MovieAdapter
    private lateinit var popularity: TextView
    private lateinit var top_rated: TextView
    private lateinit var viewModel: MainViewModel

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id){
            R.id.item_main -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.item_favourite -> {
                val intent = Intent(this, FavouriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val movies: ArrayList<Movie> = arrayListOf()
        popularity = findViewById(R.id.popularity)
        top_rated = findViewById(R.id.top_rated)
        switch = findViewById(R.id.switchSort)
        rV = findViewById(R.id.recycler)
        adapter = MovieAdapter(movies)
        adapter.setPosterClickListener(object : MovieAdapter.PosterClickListener {
            override fun onPosterClick(position: Int) {
                val movie = adapter.movies[position]
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("id", movie.id)
                startActivity(intent)
            }
        })
//        adapter.setReachEndListener(object : MovieAdapter.ReachEndListener {
//            override fun onReachEnd() {
//                Toast.makeText(
//                    this@MainActivity,
//                    "The end of list", Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
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

        val movieLiveData = viewModel.movie
        movieLiveData.observe(this) { mov ->
            adapter.movies = mov
            adapter.notifyDataSetChanged()
        }
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
        downloadData(methodOfSort, 1)
    }

    fun downloadData(methodOfSort: Int, page: Int) {
        val jsonObject = NetworkUtils.getJSONObject(methodOfSort, page)
        val jsonArray = JSONUtils.getMovieFromJSON(jsonObject)
        if (jsonArray.isNotEmpty()) {
            viewModel.deleteAllMovies()
            for (movie in jsonArray) {
                viewModel.insertMovie(movie)
            }
        }
    }
}