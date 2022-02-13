package com.android.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.data.FavouriteMovie
import com.android.mymovies.data.MainViewModel
import com.android.mymovies.data.Movie

class FavouriteActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MovieAdapter

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
        setContentView(R.layout.activity_favourite)
        recyclerView = findViewById(R.id.favouriteRecycler)
        val favouriteMovie: ArrayList<Movie> = arrayListOf()
        adapter = MovieAdapter(favouriteMovie)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val favMovies = viewModel.favouriteMovies
        favMovies.observe(this, Observer<List<FavouriteMovie>>(){
            val list: ArrayList<Movie> = arrayListOf()
            for (i in it){
                list.add(Movie.favouriteMovieAsMovie(i))
            }
            adapter.movies = list
            adapter.notifyDataSetChanged()
        })
        adapter.setPosterClickListener(object: MovieAdapter.PosterClickListener{
            override fun onPosterClick(position: Int) {
                val movie = adapter.movies[position]
                val intent = Intent(this@FavouriteActivity, DetailActivity::class.java)
                intent.putExtra("id", movie.id)
                startActivity(intent)
            }
        })
    }
}