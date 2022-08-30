package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import java.util.*
import kotlin.collections.ArrayList

class Chat : AppCompatActivity() {

    // TODO : Get the chat data
    // TODO : Implement the firebase chat like app
    lateinit var debug_text : TextView
    lateinit var recyclerView: RecyclerView
    var messages = ArrayList<String>(Arrays.asList("1test", "test2", "1test3", "1test4", "test5"))

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

        recyclerView = findViewById(R.id.chat_recyclerView)
        val chatAdaptator = ChatAdaptator(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdaptator
    }
}