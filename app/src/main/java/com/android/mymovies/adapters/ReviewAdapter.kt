package com.android.mymovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mymovies.R
import com.android.mymovies.data.Review

class ReviewAdapter(private val reviews: ArrayList<Review>): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val author = itemView.findViewById<TextView>(R.id.textViewAuthor)
        val content = itemView.findViewById<TextView>(R.id.textViewContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]
        holder.author.text = review.author
        holder.content.text = review.content
    }

    override fun getItemCount(): Int {
       return reviews.size
    }
}