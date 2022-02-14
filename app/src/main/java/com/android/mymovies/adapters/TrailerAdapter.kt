package com.android.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.R
import com.android.mymovies.data.Trailer

class TrailerAdapter(val trailers: ArrayList<Trailer>): RecyclerView.Adapter<TrailerAdapter.ViewHolder>(){
    private lateinit var onTrailerClickListener: OnTrailerClickListener

    interface OnTrailerClickListener{
        fun onTrailerClick(url: String)
    }

    fun setOnTrailerClickListener(listener: OnTrailerClickListener){
        onTrailerClickListener = listener

    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.imageViewPlay)
        val nameOfVideo = itemView.findViewById<TextView>(R.id.textViewNameOfVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trailer = trailers[position]
        holder.itemView.setOnClickListener {
            onTrailerClickListener.onTrailerClick(trailer.key)
        }
        holder.nameOfVideo.text = trailer.name
    }

    override fun getItemCount(): Int {
       return trailers.size
    }
}