package com.android.bitsandpizzas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView



class PastaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fr = inflater.inflate(R.layout.fragment_pasta, container, false) as RecyclerView
        val captions: ArrayList<String> = arrayListOf()
        val pastaId: ArrayList<Int> = arrayListOf()

        for (i in Pasta.pasta.indices){
            captions.add(Pasta.pasta[i].name)
            pastaId.add(Pasta.pasta[i].imageId)
        }
        val adapter = CaptionedImagesAdapter(captions, pastaId)
        fr.adapter = adapter
        fr.layoutManager = GridLayoutManager(inflater.context, 2)
        return fr
    }
}