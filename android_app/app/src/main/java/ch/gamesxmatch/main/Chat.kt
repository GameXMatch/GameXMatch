package ch.gamesxmatch.main

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.ChatAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.data.Message
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class Chat : AppCompatActivity() {

    // TODO : Get the chat data
    // TODO : Implement the firebase chat like app
    lateinit var matchNameText : TextView
    lateinit var recyclerView: RecyclerView
    lateinit var returnButton: ImageButton
    lateinit var sendButton: ImageButton
    lateinit var messageEditText: EditText
    lateinit var profilePictureImageView: ImageView

    var mainUser = SharedData.getInstance()
    lateinit var chatUser : User
    var id : Int = 0
    lateinit var dbRef : DatabaseReference
    var db = FirebaseDatabase.getInstance()
    lateinit var dbListener : ValueEventListener
    lateinit var chatAdaptator: ChatAdaptator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        getMatchData()
        setupMessageDisplay()
    }

    private fun initComponents(){
        setContentView(R.layout.activity_chat)
        profilePictureImageView = findViewById(R.id.chat_profile_picture)
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
            Picasso.with(this).load(chatUser.imageURL).into(profilePictureImageView)

            db.getReference("/members/").get().addOnSuccessListener { snapshot ->
                for (group in snapshot.children)
                {
                    if (group.hasChild(chatUser.uid) && group.hasChild(mainUser.getMainUser().uid)) {
                        dbRef = db.getReference("/messages/${group.key}/")

                        dbListener = object : ValueEventListener {
                            var first = true
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(first) {
                                    for (message in snapshot.children) {
                                        message.getValue<Message>()
                                            ?.let { chatAdaptator.update(it) }
                                    }
                                    first = false
                                }
                                else {

                                    val message = snapshot.children.last().getValue<Message>()
                                    if(message != null){
                                        chatAdaptator.update(message)
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        }
                        dbRef.addValueEventListener(dbListener)
                    }
                }
            }
        }
    }

    private fun setupMessageDisplay(){
        chatAdaptator = ChatAdaptator(ArrayList(), mainUser.getMainUser().uid)
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
        db.getReference("/members/").get().addOnSuccessListener { snapshot ->
            for (group in snapshot.children)
            {
                if (group.hasChild(chatUser.uid) && group.hasChild(mainUser.getMainUser().uid)) {
                    dbRef = db.getReference("/messages/${group.key}/${db.reference.push().key}/")

                    dbRef.setValue(Message(message, mainUser.getMainUser().uid, System.currentTimeMillis()))
                }
            }
        }
    }
}