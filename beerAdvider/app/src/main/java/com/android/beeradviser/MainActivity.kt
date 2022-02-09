package com.android.beeradviser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val beer: BeerExpert = BeerExpert()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.find_beer)
        val color: Spinner = findViewById(R.id.color)
        val brands: TextView = findViewById(R.id.brands)

        button.setOnClickListener{
            brands.text = beer.getBrands(color.selectedItem.toString()).toString()
        }
    }

}