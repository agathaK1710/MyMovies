package com.android.bitsandpizzas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PizzaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pizzaRecycler =
            inflater.inflate(R.layout.fragment_pizza, container, false) as RecyclerView
        val captions: ArrayList<String> = arrayListOf()
        val imageIds: ArrayList<Int> = arrayListOf()
        for (i in Pizza.pizza.indices) {
            captions.add(Pizza.pizza[i].name)
            imageIds.add(Pizza.pizza[i].imageResourceId)
        }

        val adapter = CaptionedImagesAdapter(captions, imageIds)
        val layoutManager = GridLayoutManager(inflater.context, 2)
        pizzaRecycler.adapter = adapter
        pizzaRecycler.layoutManager = layoutManager
        adapter.setListener(object: CaptionedImagesAdapter.Listener{
            override fun onClick(position: Int) {
                val intent = Intent(inflater.context, PizzaDetailActivity::class.java)
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZA_ID, position)
                startActivity(intent)
            }
        })
        return pizzaRecycler
    }
}