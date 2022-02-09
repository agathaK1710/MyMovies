package com.android.mymovies

data class Movie(
    val id: Int,
    val voteCount: Int,
    val title: String,
    val originTitle: String,
    val overview: String,
    val posterPath: String,
    val backDropPath: String,
    val voteAverage: Double,
    val releaseDate: String
) {
}