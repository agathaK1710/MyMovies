package com.android.mymovies.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application): AndroidViewModel(application) {

    val database = MovieDatabase.getInstance(getApplication())
    val movie = database.movieDao().getAllMovies()


    fun getMovieById(id: Int): Movie{
        return GetMovieTask().execute(id).get()
    }

    fun deleteAllMovies(){
        DeleteAllTask().execute()
    }

    fun insertMovie(movie: Movie){
        InsertMovieTask().execute(movie)
    }

    fun deleteMovie(movie: Movie){
        DeleteMovieClass().execute(movie)
    }

    inner class GetMovieTask: AsyncTask<Int, Void, Movie>(){
        override fun doInBackground(vararg params: Int?): Movie? {
            if(params.isNotEmpty()){
                return params[0]?.let { database.movieDao().getMovieById(it) }
            }
            return null
        }
    }

    inner class DeleteAllTask: AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            database.movieDao().deleteAllMovies()
            return null
        }
    }

    inner class InsertMovieTask: AsyncTask<Movie, Void, Void>(){
        override fun doInBackground(vararg params: Movie?): Void? {
            params[0]?.let { database.movieDao().insertMovie(it) }
            return null
        }
    }

    inner class DeleteMovieClass: AsyncTask<Movie, Void, Void>(){
        override fun doInBackground(vararg params: Movie?): Void? {
            params[0]?.let{ database.movieDao().deleteMovie(it) }
            return null
        }
    }

}