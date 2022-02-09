package com.android.startandroid

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: NoteAdapter
    private lateinit var viewModel: MainVIewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainVIewModel::class.java]
        getData()
        val rV = findViewById<RecyclerView>(R.id.recycler)
        adapter = NoteAdapter(Note.notes)
        rV.adapter = adapter
        rV.layoutManager = LinearLayoutManager(this)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }
        adapter.setListener(object : NoteAdapter.NoteClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(this@MainActivity, "lalal", Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(position: Int) {
                remove(position)
            }
        })

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                remove(viewHolder.adapterPosition)
            }
        }
        )
        itemTouchHelper.attachToRecyclerView(rV)


    }

    private fun remove(position: Int) {
        val note = Note.notes[position]
        viewModel.deleteNote(note)
    }

    private fun getData() {
        val notesFromDB = viewModel.notes
        notesFromDB.observe(this
        ) { t ->
            Note.notes.clear()
            Note.notes.addAll(t)
            adapter.notifyDataSetChanged()
        }
    }
}