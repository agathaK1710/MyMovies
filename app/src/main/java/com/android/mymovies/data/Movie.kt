package com.android.mymovies.data

data class Movie(
    val id: Int,
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
}