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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Landing : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    var sharedData = SharedData.getInstance()
    // TODO : Check for credentials
    // TODO : Nice text
    private lateinit var loginButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(checkLoggedInt()){
            getImages()
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

    fun getImages(){
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
                sharedData.setGameImages(supportedGames)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    fun checkLoggedInt() : Boolean {
        // TODO
        return true
    }



}