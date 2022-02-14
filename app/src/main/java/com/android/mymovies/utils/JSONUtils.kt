package com.android.mymovies.utils

import com.android.mymovies.data.Movie
import com.android.mymovies.data.Review
import com.android.mymovies.data.Trailer
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

        private val KEY_AUTHOR = "author"
        private val KEY_CONTENT = "content"
        private val KEY_OF_VIDEO = "key"
        private val KEY_OF_NAME = "name"
        private val BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v="
        const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/"
        const val SMALL_POSTER_SIZE = "w185"
        const val BIG_POSTER_SIZE = "w780"

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
                    val posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + getString(KEY_POSTER_PATH)
                    val bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + getString(
                        KEY_POSTER_PATH)
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
                        bigPosterPath,
                        backdropPath,
                        voteAverage,
                        releaseDate
                    )
                    result.add(movie)
                }
            }
            return result
        }

        fun getReviewFromJSON(jsonObject: JSONObject): ArrayList<Review>{
            val result: ArrayList<Review> = arrayListOf()
            val jsonArray = jsonObject.getJSONArray(KEY_RESULT)
            for(i in 0 until jsonArray.length()){
                val json = jsonArray.getJSONObject(i)
                val author = json.getString(KEY_AUTHOR)
                val content = json.getString(KEY_CONTENT)
                result.add(Review(author, content))
            }
            return result
        }

        fun getTrailerFromJSON(jsonObject: JSONObject): ArrayList<Trailer>{
            val result: ArrayList<Trailer> = arrayListOf()
            val jsonArray = jsonObject.getJSONArray(KEY_RESULT)
            for(i in 0 until jsonArray.length()){
                val json = jsonArray.getJSONObject(i)
                val key = BASE_YOUTUBE_URL + json.getString(KEY_OF_VIDEO)
                val name = json.getString(KEY_OF_NAME)
                result.add(Trailer(key, name))
            }
            return result
        }
    }
}