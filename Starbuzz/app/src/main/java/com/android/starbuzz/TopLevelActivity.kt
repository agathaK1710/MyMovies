package com.android.starbuzz

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.AsyncTask
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.android.starbuzz.DrinkActivity.Companion.EXTRA_DRINKID


class TopLevelActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var  listFavorites: ListView
    private lateinit var db: SQLiteDatabase
    private lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_level)

        listView = findViewById(R.id.list_options)
        listFavorites = findViewById(R.id.list_favorites)

        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            if(position == 0){
                val intent = Intent(this, DrinkCategoryActivity:: class.java)
                startActivity(intent)
            }
            if(position == 1){
                val intent = Intent(this, FoodCategoryActivity::class.java)
                startActivity(intent)
            }
        }

        try {
            db = StarbuzzDatabaseHelper(this).readableDatabase
            cursor = db.query(
                "DRINK",
                arrayOf("_id", "NAME"),
                "FAVORITE = 1",
                null, null, null, null
            )

            listFavorites.adapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                arrayOf("NAME"),
                intArrayOf(android.R.id.text1), 0
            )
        } catch (e: SQLiteException){
            val toast = Toast.makeText(this,  "Database is unawailable", Toast.LENGTH_SHORT)
            toast.show()
        }

        listFavorites.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DrinkActivity::class.java)
            intent.putExtra(EXTRA_DRINKID, id.toInt())
            startActivity(intent)
        }

    }

    override fun onRestart() {
        super.onRestart()
        val newCursor = db.query(
            "DRINK",
            arrayOf("_id", "NAME"),
            "FAVORITE = 1",
            null, null, null, null
        )
        val adapter = listFavorites.adapter as CursorAdapter
        adapter.changeCursor(newCursor)
        cursor = newCursor

    }

    override fun onDestroy() {
        super.onDestroy()
        cursor.close()
        db.close()
    }
}