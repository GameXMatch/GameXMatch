package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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
    lateinit var returnButton: ImageButton
    lateinit var sendButton: ImageButton
    lateinit var messageEditText: EditText

    var messages = ArrayList<String>(Arrays.asList("1test", "test2", "1test3", "1test4", "test5"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        getMatchData()
        setupMessageDisplay()
    }

    private fun initComponents(){
        setContentView(R.layout.activity_chat)
        debug_text = findViewById(R.id.chat_debug_text)
        messageEditText = findViewById(R.id.chat_editText_message)
        recyclerView = findViewById(R.id.chat_recyclerView)
        setRetunButton()
        setSendButton()

    }

    private fun getMatchData(){
        // Data from the match activity
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("matchID")
            debug_text.setText(value)
        }

        // TODO : Get messages and all the needed data
    }

    private fun setupMessageDisplay(){
        val chatAdaptator = ChatAdaptator(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdaptator
    }

    private fun setRetunButton(){
        returnButton = findViewById(R.id.chat_imageButton_back)
        returnButton.setOnClickListener{
            finish()
        }
    }

    private fun setSendButton(){
        sendButton = findViewById(R.id.chat_send_imagebutton)
        sendButton.setOnClickListener{
            val message = messageEditText.text.toString()
            sendMessage(message)
        }
    }

    private fun sendMessage(message : String){
        //TODO
        println(message)
    }
}