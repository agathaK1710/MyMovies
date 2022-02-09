package com.android.starbuzz

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class FoodCategoryActivity : AppCompatActivity() {
    private lateinit var foodsList: ListView
    private lateinit var db: SQLiteDatabase
    private lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_cathegory)

        foodsList = findViewById(R.id.list_foods)

        try{
            db = StarbuzzDatabaseHelper(this).readableDatabase
            cursor = db.query(
                "FOOD",
                arrayOf("_id", "NAME"),
                null, null, null, null, null
            )
           foodsList.adapter = SimpleCursorAdapter(
               this,
               android.R.layout.simple_list_item_1,
               cursor,
               arrayOf("NAME"),
               intArrayOf(android.R.id.text1)
           )
        } catch(e: SQLiteException){
            val toast = Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT)
            toast.show()
        }

        foodsList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, FoodActivity::class.java)
            intent.putExtra(FoodActivity.EXTRA_FOODID, id.toInt())
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor.close()
        db.close()
    }
}