package com.android.bitsandpizzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class PizzaDetailActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_detail)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pizzaId: Int = intent.extras?.getInt(EXTRA_PIZZA_ID)!!

        textView = findViewById(R.id.pizza_text)
        imageView = findViewById(R.id.pizza_image)

        textView.text = Pizza.pizza[pizzaId].name
        imageView.setImageDrawable(ContextCompat.getDrawable(this, Pizza.pizza[pizzaId].imageResourceId))
        imageView.contentDescription =  Pizza.pizza[pizzaId].name


    }

    companion object{
        const val EXTRA_PIZZA_ID = "pizza_id"
    }
}