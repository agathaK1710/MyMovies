package com.android.messenger

import android.content.Intent
import android.content.Intent.ACTION_SEND
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.android.messenger.ReceiveMessageActivity.Companion.EXTRA_MESSAGE

class CreateMessageActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_message)
        button = findViewById(R.id.send)
        editText = findViewById(R.id.message)

        button.setOnClickListener{
            val sendMessage = editText.text.toString()
            val intent = Intent(ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(EXTRA_MESSAGE, sendMessage)
            val chooser = getString(R.string.chooser)
            val newIntent = Intent.createChooser(intent, chooser)
            startActivity(newIntent)
        }
    }
}