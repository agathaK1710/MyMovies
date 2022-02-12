package com.android.mymovies.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouritesMovie")
data class FavouriteMovie(
    @PrimaryKey  val id: Int,
    val voteCount: Int,
    val title: String,
    val originTitle: String,
    val overview: String,
    val posterPath: String,
    val bigPosterPath: String,
    val backDropPath: String,
    val voteAverage: Double,
    val releaseDate: String
) {
    companion object{
        fun movieAsFavouriteMovie(movie: Movie): FavouriteMovie{
            return FavouriteMovie(movie.id, movie.voteCount, movie.title,
            movie.originTitle, movie.overview, movie.posterPath, movie.bigPosterPath,
            movie.backDropPath, movie.voteAverage, movie.releaseDate)
        }
    }
}