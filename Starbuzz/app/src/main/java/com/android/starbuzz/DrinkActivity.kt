package com.android.starbuzz

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.AsyncTask
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DrinkActivity : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var photo: ImageView
    private lateinit var favorite: CheckBox
    private lateinit var db: SQLiteDatabase

    val helper = StarbuzzDatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)

        name = findViewById(R.id.name)
        description = findViewById(R.id.description)
        photo = findViewById(R.id.photo)
        favorite = findViewById(R.id.favorite)
        val drinkId = intent.extras?.getInt(EXTRA_DRINKID)
        db = helper.readableDatabase
        try {
            val cursor = db.query(
                "DRINK",
                arrayOf("NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"),
                "_id = ?",
                arrayOf(drinkId.toString()),
                null, null, null
            )
            if (cursor.moveToFirst()) {
                name.text = cursor.getString(0)
                description.text = cursor.getString(1)
                photo.setImageResource(cursor.getInt(2))
                photo.contentDescription = cursor.getString(1)
                favorite.isChecked = cursor.getInt(3) == 1
            }

            favorite.setOnClickListener {
                UpdateDrinkTask().execute(drinkId)
            }
            cursor.close()
        } catch (e: SQLiteException) {
            val toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    inner class UpdateDrinkTask: AsyncTask<Int, Void, Boolean>() {
        private lateinit var fav: ContentValues
        private lateinit var favorite: CheckBox
        override fun onPreExecute() {
            favorite = findViewById(R.id.favorite)
            fav = ContentValues()
            fav.put("FAVORITE", favorite.isChecked)
        }

        override fun doInBackground(vararg params: Int?): Boolean {
            val drinkId = params[0]
            val helper = StarbuzzDatabaseHelper(applicationContext)
            return try {
                val db = helper.writableDatabase
                db.update(
                    "DRINK",
                    fav,
                    "_id = ?",
                    arrayOf(drinkId.toString())
                )
                db.close()
                true
            } catch (e: SQLiteException) {
                false
            }
        }

        override fun onPostExecute(result: Boolean) {
            if(!result) {
                val toast = Toast.makeText(applicationContext, "Database is unavailable", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    }


    companion object {
        const val EXTRA_DRINKID = "drinkId"
    }
}