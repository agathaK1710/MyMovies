package com.android.startandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val list: ArrayList<Note>): RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    private lateinit var listener: NoteClickListener
    interface NoteClickListener {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }

    fun setListener(lis: NoteClickListener){
        listener = lis
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        var id = 0
       with(holder){
           title.text = item.name
           description.text = item.description
           day.text = item.date
           when(item.priority){
               1 -> id = itemView.resources.getColor(android.R.color.holo_red_light)
               2 -> id = itemView.resources.getColor(android.R.color.holo_orange_light)
               3 -> id = itemView.resources.getColor(android.R.color.holo_green_light)
           }
           title.setBackgroundColor(id)
           itemView.setOnClickListener {
               listener.onClick(adapterPosition)

           }
           itemView.setOnLongClickListener {
               listener.onLongClick(adapterPosition)
               return@setOnLongClickListener true
           }
       }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.title)
        val description = itemView.findViewById<TextView>(R.id.description)
        val day = itemView.findViewById<TextView>(R.id.day)
    }
}