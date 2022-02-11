package com.android.mymovies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_NAME = "movies.db"
@Database(entities = [Movie:: class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase(){

    companion object {
        var database: MovieDatabase? = null
        val lock = Any()

        fun getInstance(context: Context): MovieDatabase {
            synchronized(lock) {
                if(database == null) {
                    database =
                        Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME).build()
                }
                return database!!
            }
        }
    }
    abstract fun movieDao(): MovieDAO
}