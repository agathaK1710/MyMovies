package com.android.joke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.button)

        btn.setOnClickListener {
            val intent = Intent(this, DelayedMessageService::class.java)
            intent.putExtra(DelayedMessageService.EXTRA_MESSAGE, resources.getString(R.string.response))
            startService(intent)
        }
    }
}