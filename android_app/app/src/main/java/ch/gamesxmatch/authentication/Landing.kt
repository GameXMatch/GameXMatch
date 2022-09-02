package ch.gamesxmatch.authentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ch.gamesxmatch.R
import ch.gamesxmatch.data.Game
import ch.gamesxmatch.main.CoreApp
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Landing : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    var sharedData = SharedData.getInstance()
    // TODO : Check for credentials
    // TODO : Nice text
    private lateinit var loginButton : Button
    val uuid = sharedData.getMainUserUUID()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(checkLoggedInt()){
            getGames()
            getUserData()
            val intent = Intent(this, CoreApp::class.java)

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
                finish()
            }, 3000)

        } else {
            setContentView(R.layout.activity_landing)
            loginButton = findViewById(R.id.landing_connect_button)
            val intent = Intent(this, Login::class.java)
            loginButton.setOnClickListener {
                startActivity(intent)
            }
        }
    }

    fun getGames() {
        db = Firebase.firestore
        val supportedGames = ArrayList<Game>()
        db.collection("Games")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("${document.id} => ${document.data}")
                    val currentGame = Game()
                    currentGame.id = document.id
                    currentGame.setImageBase64(document.data.get("imageURL").toString())
                    currentGame.name = document.data.get("name").toString()
                    supportedGames.add(currentGame)
                }
                sharedData.setGames(supportedGames)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    fun getUserData() {
        val docRef = db.collection("Users").document(uuid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    println("DocumentSnapshot data: ${document.data}")
                    var user = User()
                    user.name = document.data?.get("name").toString()
                    user.description = document.data?.get("desc").toString()
                    user.imageData = document.data?.get("imageURL").toString()
                    user.uid = uuid

                    val games = document.data?.get("games") as ArrayList<DocumentReference>
                    for(game in games){
                        user.gamesUIDs.add(game.path)
                    }

                    val likes = document.data?.get("games") as ArrayList<DocumentReference>
                    for(like in likes){
                        user.likes.add(like.path)
                    }

                    val dislikes = document.data?.get("games") as ArrayList<DocumentReference>
                    for(dislike in dislikes){
                        user.dislikes.add(dislike.path)
                    }

                    sharedData.setMainUser(user)
                } else {
                    println("No such document")
                }
            }
            .addOnFailureListener { exception ->
                println("get failed with $exception")
            }
    }

    fun checkLoggedInt() : Boolean {
        // TODO
        return true
    }



}