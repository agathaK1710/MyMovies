package com.android.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.mymovies.data.FavouriteMovie
import com.android.mymovies.data.MainViewModel
import com.android.mymovies.data.Movie
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var originalTitle: TextView
    private lateinit var rating: TextView
    private lateinit var date: TextView
    private lateinit var description: TextView
    private lateinit var viewModel: MainViewModel
    private var favouriteMovie: FavouriteMovie? = null
    private lateinit var favouriteView: ImageView
    private var id: Int? = null
    private var movie: Movie? = null

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
        favouriteView.setOnClickListener {
            if(favouriteMovie == null) {
                viewModel.insertFavouriteMovie(FavouriteMovie.movieAsFavouriteMovie(movie!!))
                Toast.makeText(this, getString(R.string.add_to_favourite), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.deleteFavouriteMovie(favouriteMovie!!)
                Toast.makeText(this, getString(R.string.remove_from_favourite), Toast.LENGTH_SHORT).show()
            }
            setFavourite()
        }
    }

    fun setFavourite(){
        favouriteMovie = id?.let{viewModel.getFavouriteMovieById(it)}
        if(favouriteMovie == null){
            favouriteView.setImageResource(R.drawable.ic_baseline_star_rate_24)
        } else {
            favouriteView.setImageResource(R.drawable.star_yellow)
        }
    }
}