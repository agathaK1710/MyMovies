package com.android.starbuzz

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class FoodActivity : AppCompatActivity() {
    private lateinit var foodName: TextView
    private lateinit var foodDescription: TextView
    private lateinit var foodPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        foodName = findViewById(R.id.food_name)
        foodDescription = findViewById(R.id.food_description)
        foodPhoto = findViewById(R.id.food_photo)

        val foodId = intent.extras?.getInt(EXTRA_FOODID)

        try{
            val db = StarbuzzDatabaseHelper(this).readableDatabase
            val cursor = db.query(
                "FOOD",
                arrayOf( "NAME", "DESCRIPTION","IMAGE_RESOURCE_ID"),
                "_id = ?",
                arrayOf(foodId.toString()),
                null, null, null
            )
            if(cursor.moveToFirst()) {
                foodName.text = cursor.getString(0)
                foodDescription.text = cursor.getString(1)
                foodPhoto.setImageResource(cursor.getInt(2))
                foodPhoto.contentDescription = cursor.getString(1)
            }

            cursor.close()
            db.close()
        }catch (e: SQLiteException){
            val toast = Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    companion object{
        const val EXTRA_FOODID = "food_id"
    }
}