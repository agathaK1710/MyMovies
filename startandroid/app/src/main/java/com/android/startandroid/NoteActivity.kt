package com.android.startandroid

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider

class NoteActivity : AppCompatActivity() {
    private lateinit var saveBtn: Button
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var spinner: Spinner
    private lateinit var radioGroup: RadioGroup
    private lateinit var viewModel: MainVIewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        viewModel = ViewModelProvider(this)[MainVIewModel::class.java]
        saveBtn = findViewById(R.id.saveBtn)
        title = findViewById(R.id.editTextTitle)
        description = findViewById(R.id.editTextDescription)
        spinner = findViewById(R.id.spinner)
        radioGroup = findViewById(R.id.radioGroup)
        saveBtn.setOnClickListener {
            val title = title.text.toString()
            val description = description.text.toString()
            val day = spinner.selectedItem.toString()
            val id = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(id)
            val priority: Int = radioButton.text.toString().toInt()

            if (isFilled(title, description)) {
                val note = Note(id, title, description, day, priority)
                viewModel.insertNote(note)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "All values must be fill", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isFilled(title: String, description: String): Boolean {
        return (title.isNotEmpty() && description.isNotEmpty())
    }
}