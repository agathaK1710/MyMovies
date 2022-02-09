package com.android.startandroid

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainVIewModel(application: Application) : AndroidViewModel(application) {
    val db = NotesDatabase.getInstance(getApplication())
    val notes = db.notesDao().getAllNotes()

    fun insertNote(note: Note){
        InsertTask().execute(note)
    }

    fun deleteNote(note: Note){
        DeleteTask().execute(note)
    }

    fun deleteAllNote(){
        DeleteTask().execute()
    }

    inner class InsertTask: AsyncTask<Note, Void, Void>(){
        override fun doInBackground(vararg params: Note): Void? {
            if(params.isNotEmpty()) {
                db.notesDao().insertNote(params[0])
            }
            return null
        }

    }

    inner class DeleteTask: AsyncTask<Note, Void, Void>(){
        override fun doInBackground(vararg params: Note): Void? {
            if(params.isNotEmpty()) {
                db.notesDao().deleteNote(params[0])
            }
            return null
        }

    }

    inner class DeleteAllTask: AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void): Void? {
            if(params.isNotEmpty()) {
                db.notesDao().deleteAll()
            }
            return null
        }

    }

}