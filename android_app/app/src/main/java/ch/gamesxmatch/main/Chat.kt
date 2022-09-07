package ch.gamesxmatch.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptor.ChatAdaptor
import ch.gamesxmatch.R
import ch.gamesxmatch.data.Message
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

/**
 * Activity serving the purpose of being a live chat between two users
 *
 * This activity transitions to :
 *  - The profile of the match the user is chatting with (MatchProfile)
 *  - The last activity that was active before arriving here. This activity should be the
 *    the main app, on the fragment displaying the matches (CoreApp / Match)
 */
class Chat : AppCompatActivity() {

    // Components
    lateinit var matchNameText : TextView
    lateinit var recyclerView: RecyclerView
    lateinit var returnButton: ImageButton
    lateinit var sendButton: ImageButton
    lateinit var messageEditText: EditText
    lateinit var profilePictureImageView: ImageView

    // Data
    var mainUser = SharedData.getInstance()
    lateinit var chatUser : User
    lateinit var dbRef : DatabaseReference
    var db = FirebaseDatabase.getInstance()
    lateinit var dbListener : ValueEventListener
    lateinit var chatAdaptator: ChatAdaptor
    var firstClick = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        getMatchData()
        initChatDataLogic()
    }

    /**
     * Initialise the components
     */
    private fun initComponents(){
        setContentView(R.layout.activity_chat)
        initProfileInfo()
        initReturnButton()
        initChatComponents()
    }

    /**
     * Initialises the button closing the chat
     */
    private fun initReturnButton(){
        returnButton = findViewById(R.id.chat_imageButton_back)
        returnButton.setOnClickListener{
            finish()
        }
    }

    /**
     * Initialises the different components of the chat
     */
    private fun initChatComponents(){
        sendButton = findViewById(R.id.chat_send_imagebutton)

        // Send message and reset the value on message sent
        sendButton.setOnClickListener{
            val message = messageEditText.text.toString()
            sendMessage(message)
            messageEditText.setText("")
        }

        messageEditText = findViewById(R.id.chat_editText_message)

        // Delete "enter your message here" on click
        messageEditText.setOnFocusChangeListener{ view: View, b: Boolean ->
            if(firstClick){
                messageEditText.setText("")
                firstClick = false
            }
        }

        // Chat display
        recyclerView = findViewById(R.id.chat_recyclerView)

        chatAdaptator = ChatAdaptor(ArrayList(), mainUser.getMainUser().uid)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdaptator
    }

    /**
     * Initialises the components that will hold the match's data for this activity
     */
    private fun initProfileInfo(){
        profilePictureImageView = findViewById(R.id.chat_profile_picture)
        matchNameText = findViewById(R.id.chat_matchName_text)

        // Click listeners for the transition to the user's profile
        matchNameText.setOnClickListener{
            redirectToProfile()
        }
        profilePictureImageView.setOnClickListener{
            redirectToProfile()
        }
    }

    /**
     * Transition to the match's profile
     */
    private fun redirectToProfile() {
        val intent = Intent(this, MatchProfile::class.java)
        intent.putExtra("match", chatUser)
        startActivity(intent)
    }

    /**
     * Returns the data of the match if it exits. Closes the activity otherwise
     */
    private fun getMatchData() {
        // Data from the match activity
        val extras = intent.extras
        if (extras != null) {
            chatUser = extras.get("match") as User
            matchNameText.setText(chatUser.name)
            Picasso.with(this).load(chatUser.imageURL).into(profilePictureImageView)
        } else {
            finish()
        }
    }

    /**
     * Handles the fetching of messages and the UI update. Handles finding the user uuid pair.
     */
    private fun initChatDataLogic() {
        db.getReference("members").get().addOnSuccessListener { snapshot ->
            for (group in snapshot.children) {
                if (group.hasChild(chatUser.uid) && group.hasChild(mainUser.getMainUser().uid)) {
                    dbRef = db.getReference("messages/${group.key}")
                    initMessageListener()
                }
            }
        }
    }

    /**
     * Allows the activity to get the messages and listens for new ones
     */
    private fun initMessageListener() {
        dbListener = object : ValueEventListener {
            var first = true
            override fun onDataChange(snapshot: DataSnapshot) {
                if(first) {
                    getAllMessages(snapshot)
                    first = false
                }
                else {
                    getLastMessage(snapshot)
                }
                // Set the view height
                recyclerView.scrollToPosition((snapshot.childrenCount - 1).toInt())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        dbRef.addValueEventListener(dbListener)
    }

    /**
     * Gets all the messages of a discussion. Used for the first access of the chat
     */
    private fun getAllMessages(snapshot : DataSnapshot) {
        for (message in snapshot.children) {
            message.getValue<Message>()
                ?.let { chatAdaptator.update(it) }
        }
    }

    /**
     * Gets the last added message. Used once the chat is up and running, waiting for new messages
     */
    private fun getLastMessage(snapshot : DataSnapshot) {
        val message = snapshot.children.last().getValue<Message>()
        if(message != null){
            chatAdaptator.update(message)
        }
    }


    /**
     * Sends a query to the database
     */
    private fun sendMessage(message : String){
        db.getReference("members/").get().addOnSuccessListener { snapshot ->
            for (group in snapshot.children) {
                if (group.hasChild(chatUser.uid) && group.hasChild(mainUser.getMainUser().uid)) {
                    dbRef = db.getReference("messages/${group.key}/${db.reference.push().key}")

                    dbRef.setValue(Message(message, mainUser.getMainUser().uid, System.currentTimeMillis()))
                        .addOnFailureListener{
                                exception ->  Log.d("MESSAGE", "get failed with ", exception)
                        }
                    break
                }
            }
        }.addOnFailureListener { exception ->
            Log.d("MESSAGE", "get failed with ", exception)
        }
    }
}