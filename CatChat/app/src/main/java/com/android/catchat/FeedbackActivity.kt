package com.android.catchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar


class FeedbackActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}