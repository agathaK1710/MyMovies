package com.android.startandroid

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note(@PrimaryKey(autoGenerate = true) val id: Int, val name: String, val description: String, val date: String, val priority: Int) {
    companion object{
        val notes: ArrayList<Note> = arrayListOf()
    }
}