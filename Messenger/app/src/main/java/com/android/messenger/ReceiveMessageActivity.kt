package com.android.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ReceiveMessageActivity : AppCompatActivity() {

    private lateinit var message: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_message)
        message = findViewById(R.id.message)
        message.text = intent.getStringExtra(EXTRA_MESSAGE)
    }

    companion object{
        const val EXTRA_MESSAGE = "message"
    }
}
