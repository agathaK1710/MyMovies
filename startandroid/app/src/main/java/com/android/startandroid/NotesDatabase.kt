package com.android.startandroid

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    companion object {
        var database: NotesDatabase? = null
        val DB_NAME = "notes2.db"
        val monitor = Any()
        fun getInstance(context: Context): NotesDatabase {
            synchronized(monitor) {
                if (database == null) {
                    database = Room.databaseBuilder(context, NotesDatabase::class.java, DB_NAME)
                        .build()
                }
                return database!!
            }
        }
    }

    abstract fun notesDao(): NoteDao
}