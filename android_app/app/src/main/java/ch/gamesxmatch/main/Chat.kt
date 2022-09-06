package ch.gamesxmatch.main

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.ChatAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import java.util.*
import kotlin.collections.ArrayList

class Chat : AppCompatActivity() {

    // TODO : Get the chat data
    // TODO : Implement the firebase chat like app
    lateinit var matchNameText : TextView
    lateinit var recyclerView: RecyclerView
    lateinit var returnButton: ImageButton
    lateinit var sendButton: ImageButton
    lateinit var messageEditText: EditText

    var mainUser = SharedData.getInstance()
    lateinit var chatUser : User
    var id : Int = 0

    var messages = ArrayList<String>(Arrays.asList("1test", "test2", "1test3", "1test4", "test5"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        getMatchData()
        setupMessageDisplay()
    }

    private fun initComponents(){
        setContentView(R.layout.activity_chat)
        recyclerView = findViewById(R.id.chat_recyclerView)
        initProfileInfo()
        setRetunButton()
        setSendButton()

    }

    private fun getMatchData(){
        // Data from the match activity
        val extras = intent.extras
        if (extras != null) {
            id = extras.getInt("matchID")

            chatUser = mainUser.getMatches()[id]

            matchNameText.setText(chatUser.name)
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

    private fun initProfileInfo(){
        matchNameText = findViewById(R.id.chat_matchName_text)
        messageEditText = findViewById(R.id.chat_editText_message)
        matchNameText.setOnClickListener{
            redirectToProfile()
        }
        messageEditText.setOnClickListener{
            redirectToProfile()
        }
    }

    private fun redirectToProfile(){
        // TODO
        val intent = Intent(this, MatchProfile::class.java)
        intent.putExtra("matchID", id)
        startActivity(intent)
        println("clicked")
    }

    private fun sendMessage(message : String){
        //TODO
        println(message)
    }
}