package com.android.starbuzz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB_NAME = "starbuzz"
private const val DB_VERSION = 2

class StarbuzzDatabaseHelper(val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        updateMyDatabase(db, 0, DB_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        updateMyDatabase(db, oldVersion, newVersion)
    }

    fun insertInDb(
        db: SQLiteDatabase?,
        tableName: String,
        name: String,
        description: String,
        imageId: Int
    ) {
        val value = ContentValues()
        value.put("NAME", name)
        value.put("DESCRIPTION", description)
        value.put("IMAGE_RESOURCE_ID", imageId)
        db?.insert(tableName, null, value)
    }


    fun updateMyDatabase(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1) {
            db?.execSQL(
                "CREATE TABLE DRINK(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "NAME TEXT," +
                        "DESCRIPTION TEXT," +
                        "IMAGE_RESOURCE_ID INTEGER);"
            )
            db?.execSQL(
                "CREATE TABLE FOOD(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "NAME TEXT," +
                        "DESCRIPTION TEXT," +
                        "IMAGE_RESOURCE_ID INTEGER);"
            )

            insertInDb(db, "DRINK", "Latte", "Espresso and steamed milk", R.drawable.latte)
            insertInDb(
                db, "DRINK", "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                R.drawable.cappuccino
            )
            insertInDb(db, "DRINK", "Filter", "Our best drip coffee", R.drawable.filter)

            insertInDb(db, "FOOD", "Burger", "MMMMM!", R.drawable.burger)
            insertInDb(db, "FOOD", "Potatoes", "Potatoes - free", R.drawable.free)
            insertInDb(db, "FOOD", "Ice_cream", "YAMMY", R.drawable.ice)
        }

        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC")
        }
    }
}