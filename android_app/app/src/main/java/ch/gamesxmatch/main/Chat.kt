package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ch.gamesxmatch.R

class Chat : AppCompatActivity() {

    // TODO : Get the chat data
    // TODO : Implement the firebase chat like app
    lateinit var debug_text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        debug_text = findViewById(R.id.chat_debug_text)

        // Data from the
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("matchID")
            debug_text.setText(value)
        }
    }
}