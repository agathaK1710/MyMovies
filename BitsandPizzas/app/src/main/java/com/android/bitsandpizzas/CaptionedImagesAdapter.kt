package com.android.bitsandpizzas

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CaptionedImagesAdapter(val captions: ArrayList<String>, val imageIds: ArrayList<Int>) :
    RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>() {

    private lateinit var listener: Listener

    interface Listener {
        fun onClick(position: Int)
    }

    fun setListener(li: Listener){
        listener = li
    }

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val imageView = cardView.findViewById<ImageView>(R.id.info_image)
        val textView = cardView.findViewById<TextView>(R.id.info_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_caption_image, parent, false) as CardView
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val drawable = ContextCompat.getDrawable(cardView.context, imageIds[position])
        with(holder) {
            textView.text = captions[position]
            imageView.setImageDrawable(drawable)
            imageView.contentDescription = captions[position]
        }

        cardView.setOnClickListener {
            if(listener!=null){
                listener.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return captions.size
    }
}


