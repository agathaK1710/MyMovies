package com.android.startandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val NAME_DB = "notesDb"
const val VERSION_DB = 1

class NoteDB(context: Context) : SQLiteOpenHelper(context, NAME_DB, null, VERSION_DB) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "description TEXT," +
                "day TEXT," +
                "priority INTEGER );")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        const val TABLE_NAME = "notes"
    }
}