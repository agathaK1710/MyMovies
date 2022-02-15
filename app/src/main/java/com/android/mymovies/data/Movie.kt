package com.android.mymovies.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class Movie {
    @PrimaryKey(autoGenerate = true)
    var uniqueId: Int = 0
    var id: Int
    var voteCount: Int
    var title: String
    var originTitle: String
    var overview: String
    var posterPath: String
    var bigPosterPath: String
    var backDropPath: String
    var voteAverage: Double
    var releaseDate: String

    constructor(
        uniqueId: Int,
        id: Int,
        voteCount: Int,
        title: String,
        originTitle: String,
        overview: String,
        posterPath: String,
        bigPosterPath: String,
        backDropPath: String,
        voteAverage: Double,
        releaseDate: String
    ){
        this.uniqueId = uniqueId
        this.id = id
        this.voteCount = voteCount
        this.title = title
        this.originTitle = originTitle
        this.overview = overview
        this.posterPath = posterPath
        this.bigPosterPath = bigPosterPath
        this.backDropPath = backDropPath
        this.voteAverage = voteAverage
        this.releaseDate = releaseDate
    }

    @Ignore
    constructor(
        id: Int,
        voteCount: Int,
        title: String,
        originTitle: String,
        overview: String,
        posterPath: String,
        bigPosterPath: String,
        backDropPath: String,
        voteAverage: Double,
        releaseDate: String
    ){
        this.id = id
        this.voteCount = voteCount
        this.title = title
        this.originTitle = originTitle
        this.overview = overview
        this.posterPath = posterPath
        this.bigPosterPath = bigPosterPath
        this.backDropPath = backDropPath
        this.voteAverage = voteAverage
        this.releaseDate = releaseDate
    }


    companion object {
        fun favouriteMovieAsMovie(movie: FavouriteMovie): Movie {
            return Movie(
                movie.id, movie.voteCount, movie.title,
                movie.originTitle, movie.overview, movie.posterPath, movie.bigPosterPath,
                movie.backDropPath, movie.voteAverage, movie.releaseDate
            )
        }
    }
}