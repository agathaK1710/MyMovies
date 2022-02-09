package com.android.bitsandpizzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class OrderActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val snackbar = Snackbar.make(
                findViewById(R.id.coordinator),
                "Your order has been updated",
                Snackbar.LENGTH_SHORT
            )

            snackbar.setAction("UNDO", View.OnClickListener {
                val toast = Toast.makeText(this, "Undone", Toast.LENGTH_SHORT)
                toast.show()
            })
            snackbar.show()
        }
    }
}