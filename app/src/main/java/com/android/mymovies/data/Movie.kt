package com.android.mymovies.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,
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