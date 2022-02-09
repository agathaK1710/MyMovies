package com.android.mymovies.utils

import com.android.mymovies.Movie
import org.json.JSONObject

class JSONUtils {
    companion object {
        private val KEY_RESULT = "results"
        private val KEY_VOTE_COUNT = "vote_count"
        private val KEY_ID = "id"
        private val KEY_TITLE = "title"
        private val KEY_ORIGINAL_TITLE = "original_title"
        private val KEY_OVERVIEW = "overview"
        private val KEY_POSTER_PATH = "poster_path"
        private val KEY_BACKDROP_PATH = "backdrop_path"
        private val KEY_VOTE_AVERAGE = "vote_average"
        private val KEY_RELEASE_DATE = "release_date"

        fun getMovieFromJSON(jsonObject: JSONObject): ArrayList<Movie> {
            val result: ArrayList<Movie> = arrayListOf()
            val jsonArray = jsonObject.getJSONArray(KEY_RESULT)
            for (i in 0 until jsonArray.length()) {
                val jsonMovie = jsonArray.getJSONObject(i)
                with(jsonMovie) {
                    val voteCount = getInt(KEY_VOTE_COUNT)
                    val id = getInt(KEY_ID)
                    val title = getString(KEY_TITLE)
                    val originalTitle = getString(KEY_ORIGINAL_TITLE)
                    val overview = getString(KEY_OVERVIEW)
                    val posterPath = getString(KEY_POSTER_PATH)
                    val backdropPath = getString(KEY_BACKDROP_PATH)
                    val voteAverage = getDouble(KEY_VOTE_AVERAGE)
                    val releaseDate = getString(KEY_RELEASE_DATE)
                    val movie = Movie(
                        id,
                        voteCount,
                        title,
                        originalTitle,
                        overview,
                        posterPath,
                        backdropPath,
                        voteAverage,
                        releaseDate
                    )
                    result.add(movie)
                }
            }
            return result
        }
    }
}