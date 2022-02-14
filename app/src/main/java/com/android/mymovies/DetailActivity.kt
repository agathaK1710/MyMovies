package com.android.mymovies

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.adapters.ReviewAdapter
import com.android.mymovies.adapters.TrailerAdapter
import com.android.mymovies.data.*
import com.android.mymovies.utils.JSONUtils
import com.android.mymovies.utils.NetworkUtils
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var originalTitle: TextView
    private lateinit var rating: TextView
    private lateinit var date: TextView
    private lateinit var description: TextView
    private lateinit var viewModel: MainViewModel
    private lateinit var rVTrailers: RecyclerView
    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var rVReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private var favouriteMovie: FavouriteMovie? = null
    private lateinit var favouriteView: ImageView
    private var id: Int? = null
    private var movie: Movie? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        setContentView(R.layout.activity_detail)
        imageView = findViewById(R.id.imageViewBigPoster)
        title = findViewById(R.id.textViewTitle)
        originalTitle = findViewById(R.id.textViewOriginalTitle)
        rating = findViewById(R.id.textViewRating)
        date = findViewById(R.id.textViewDate)
        description = findViewById(R.id.textViewDescription)
        favouriteView = findViewById(R.id.imageViewSaveFavoirite)

        id = intent.extras?.getInt("id")
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        movie = id?.let { viewModel.getMovieById(it) }
        Picasso.get().load(movie?.bigPosterPath).into(imageView)
        title.text = movie?.title.toString()
        originalTitle.text = movie?.originTitle.toString()
        rating.text = movie?.voteAverage.toString()
        date.text = movie?.originTitle.toString()
        description.text = movie?.overview.toString()
        setFavourite()

        val jsonReview = movie?.let { NetworkUtils.getJSONObjectForReviews(it.id) }
        val jsonTrailer = movie?.let { NetworkUtils.getJSONObjectForVideos(it.id) }
        val reviews = JSONUtils.getReviewFromJSON(jsonReview!!)
        val trailers = JSONUtils.getTrailerFromJSON(jsonTrailer!!)
        rVReviews = findViewById(R.id.recycler_review)
        reviewAdapter = ReviewAdapter(reviews)
        rVReviews.adapter = reviewAdapter
        rVReviews.layoutManager = LinearLayoutManager(this)

        rVTrailers = findViewById(R.id.recycler_video)
        trailerAdapter = TrailerAdapter(trailers)
        rVTrailers.adapter = trailerAdapter
        rVTrailers.layoutManager = LinearLayoutManager(this)
        trailerAdapter.setOnTrailerClickListener(object : TrailerAdapter.OnTrailerClickListener {
            override fun onTrailerClick(url: String) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        })

        favouriteView.setOnClickListener {
            if (favouriteMovie == null) {
                viewModel.insertFavouriteMovie(FavouriteMovie.movieAsFavouriteMovie(movie!!))
                Toast.makeText(this, getString(R.string.add_to_favourite), Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.deleteFavouriteMovie(favouriteMovie!!)
                Toast.makeText(this, getString(R.string.remove_from_favourite), Toast.LENGTH_SHORT)
                    .show()
            }
            setFavourite()
        }
    }

    fun setFavourite() {
        favouriteMovie = id?.let { viewModel.getFavouriteMovieById(it) }
        if (favouriteMovie == null) {
            favouriteView.setImageResource(R.drawable.ic_baseline_star_rate_24)
        } else {
            favouriteView.setImageResource(R.drawable.star_yellow)
        }
    }
}