package com.android.mymovies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.adapters.MovieAdapter
import com.android.mymovies.data.MainViewModel
import com.android.mymovies.data.Movie
import com.android.mymovies.utils.JSONUtils
import com.android.mymovies.utils.NetworkUtils
import org.json.JSONObject

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<JSONObject> {
    private lateinit var rV: RecyclerView
    private lateinit var switch: SwitchCompat
    private lateinit var adapter: MovieAdapter
    private lateinit var popularity: TextView
    private lateinit var top_rated: TextView
    private lateinit var viewModel: MainViewModel
    private var methodOfSort: Int = 0
    private val LOADER_ID = 170
//    private var page = 1
    //private var isLoading = false
    private lateinit var loaderManager: LoaderManager

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
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
        loaderManager = LoaderManager.getInstance(this)
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
//                if(!isLoading) {
//                    downloadData(methodOfSort, page)
//                }
//            }
//        })
        rV.adapter = adapter
        rV.layoutManager = GridLayoutManager(this, 2)
        switch.isChecked = true
        switch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//                page = 1
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
        }
    }

    fun setMethodOfSort(isChecked: Boolean) {
        methodOfSort = if (isChecked) {
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
        val url = NetworkUtils.buildURL(methodOfSort, page)
        val bundle = Bundle()
        bundle.putString("url", url.toString())
        loaderManager.restartLoader(LOADER_ID, bundle, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<JSONObject?> {
        val loader = NetworkUtils.Companion.JSONLoader(this, args)
//        loader.setOnStartLoadingListener(object: NetworkUtils.Companion.JSONLoader.OnStartLoadingListener{
//            override fun onStartLoading() {
//                isLoading = true
//            }
//        })
        return loader
    }

    override fun onLoadFinished(loader: Loader<JSONObject>, data: JSONObject?) {
        val jsonArray = JSONUtils.getMovieFromJSON(data)
        if (jsonArray.isNotEmpty()) {
            viewModel.deleteAllMovies()
            for (movie in jsonArray) {
                viewModel.insertMovie(movie)
            }
            adapter.movies = jsonArray
            adapter.notifyDataSetChanged()
//            page++
        }
//        isLoading = false
        loaderManager.destroyLoader(LOADER_ID)

    }

    override fun onLoaderReset(loader: Loader<JSONObject>) {
        TODO("Not yet implemented")
    }
}