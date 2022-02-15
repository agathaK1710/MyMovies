package com.android.mymovies.utils

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class NetworkUtils {
    companion object {
        private val BASE_URL = "https://api.themoviedb.org/3/discover/movie"
        private val BASE_URL_VIDEOS = "https://api.themoviedb.org/3/movie/%s/videos"
        private val BASE_URL_REVIEWS = "https://api.themoviedb.org/3/movie/%s/reviews"
        private val PARAMS_API_KEY = "api_key"
        private val PARAMS_LANGUAGE = "language"
        private val PARAMS_SORT_BY = "sort_by"
        private val PARAMS_PAGE = "page"
        private val PARANS_MIN_VOTE_COUNT = "vote_count.gte"

        private val API_KEY = "cdcf6a1d2e71771f370a0beaed0070a4"
        private val LANGUAGE_VALUE = "ru-RU"
        private val SORT_BY_POPULARITY = "popularity.desc"
        private val SORT_BY_TOP_RATED = "vote_average.desc"
        private val MIN_VOTE_COUNT_VALUE = "1000"

        const val POPULARITY = 0
        const val TOP_RATED = 1

         fun buildURLForVideos(id: Int): URL{
            val uri = Uri.parse(String.format(BASE_URL_VIDEOS, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .build()
            return URL(uri.toString())
        }

         fun buildURLForReviews(id: Int): URL{
            val uri = Uri.parse(String.format(BASE_URL_REVIEWS, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)

                .build()
            return URL(uri.toString())
        }

         fun buildURL(sortBy: Int, page: Int): URL {
            val methodOfSort = if (sortBy == POPULARITY) SORT_BY_POPULARITY else SORT_BY_TOP_RATED
            val uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, page.toString())
                .appendQueryParameter(PARANS_MIN_VOTE_COUNT, MIN_VOTE_COUNT_VALUE)
                .build()
            return URL(uri.toString())
        }

        fun getJSONObject(sortBy: Int, page: Int): JSONObject{
            val url = buildURL(sortBy, page)
            return JSONLoadTask().execute(url).get()
        }

        fun getJSONObjectForVideos(id: Int): JSONObject{
            val url = buildURLForVideos(id)
            return JSONLoadTask().execute(url).get()
        }

        fun getJSONObjectForReviews(id: Int): JSONObject{
            val url = buildURLForReviews(id)
            return JSONLoadTask().execute(url).get()
        }

        class JSONLoader(context: Context, val bundle: Bundle?): AsyncTaskLoader<JSONObject?>(context) {
//            private lateinit var onStartLoadingListener: OnStartLoadingListener
//
//            interface OnStartLoadingListener{
//                fun onStartLoading(){}
//            }
//
//            fun setOnStartLoadingListener(listener: OnStartLoadingListener){
//                onStartLoadingListener = listener
//            }
            override fun loadInBackground(): JSONObject? {
                if(bundle == null) return null
                val urlAsString = bundle.getString("url")
                var url: URL? = null
                try {
                    url = URL(urlAsString)
                } catch (e: MalformedURLException){
                    e.printStackTrace()
                }
                if (url == null) return null
                val connection = url.openConnection() as HttpURLConnection
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

            override fun onStartLoading() {
                super.onStartLoading()
//                onStartLoadingListener.onStartLoading()
                forceLoad()
            }
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