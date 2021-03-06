package com.android.mymovies.utils

import android.net.Uri
import android.os.AsyncTask
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class NetworkUtils {
    companion object {
        private val BASE_URL = "https://api.themoviedb.org/3/discover/movie"
        private val PARAMS_API_KEY = "api_key"
        private val PARAMS_LANGUAGE = "language"
        private val PARAMS_SORT_BY = "sort_by"
        private val PARAMS_PAGE = "page"

        private val API_KEY = "cdcf6a1d2e71771f370a0beaed0070a4"
        private val LANGUAGE_VALUE = "ru-RU"
        private val SORT_BY_POPULARITY = "popularity.desc"
        private val SORT_BY_TOP_RATED = "vote_average.desc"

        const val POPULARITY = 0

        private fun buildURL(sortBy: Int, page: Int): URL? {
            val methodOfSort = if (sortBy == POPULARITY) SORT_BY_POPULARITY else SORT_BY_TOP_RATED
            val uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, page.toString())
                .build()
            return URL(uri.toString())
        }

        fun getJSONObject(sortBy: Int, page: Int): JSONObject{
            val url = buildURL(sortBy, page)
            return JSONLoadTask().execute(url).get()
        }

        private class JSONLoadTask : AsyncTask<URL, Void, JSONObject>() {
            override fun doInBackground(vararg params: URL?): JSONObject {
                val connection = params[0]?.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)
                val reader = BufferedReader(inputStreamReader)
                val builder = StringBuilder()
                var line = reader.readLine()
                while (line != null) {
                    builder.append(line)
                    line = reader.readLine()
                }
                connection.disconnect()
                return JSONObject(builder.toString())
            }
        }

    }
}