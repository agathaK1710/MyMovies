package com.android.starbuzz

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class DrinkCategoryActivity : AppCompatActivity() {
    private lateinit var cursor: Cursor
    private lateinit var db: SQLiteDatabase
    private lateinit var listDrinks: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_cathegory)
        listDrinks = findViewById(R.id.list_drinks)
        try {
            val helper = StarbuzzDatabaseHelper(this)
            db = helper.readableDatabase
            cursor = db.query(
                "Drink",
                arrayOf("_id", "NAME"),
                null, null, null, null, null
            )

            listDrinks.adapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                arrayOf("NAME"),
                intArrayOf(android.R.id.text1)
            )

        } catch (e: SQLiteException) {
            val toast = Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT)
            toast.show()
        }

        listDrinks.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, DrinkActivity::class.java)
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, id.toInt())
                startActivity(intent)
            }
    }

    override fun onDestroy() {
        cursor.close()
        db.close()
        super.onDestroy()
    }
}