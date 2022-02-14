package com.android.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.R
import com.android.mymovies.data.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private lateinit var posterClickListener: PosterClickListener
    //private lateinit var reachEndListener: ReachEndListener

    interface PosterClickListener{
        fun onPosterClick(position: Int){}
    }
    
    interface ReachEndListener{
        fun onReachEnd(){}
    }

    fun setPosterClickListener(posterListener: PosterClickListener){
        posterClickListener = posterListener
    }

//    fun setReachEndListener(reachListener: ReachEndListener){
//        reachEndListener = reachListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(movies[position].posterPath).into(holder.imageView)
        holder.itemView.setOnClickListener {
            posterClickListener.onPosterClick(position)
        }
//        if(position > movies.size - 4){
//            reachEndListener.onReachEnd()
//        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageViewSmallPoster)
    }
}