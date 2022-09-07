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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

/**
 * Launch/initial activity. It gives the option to the user to log in. In the case of a successful
 * login, the app will query the database in order to load the necessary data
 *
 * This activity transitions to :
 *  - The login page, if the user didn't log in yet (Login)
 *  - The main part of the app if the user is logged in (CoreApp)
 */
class Landing : AppCompatActivity() {

    // Component
    private lateinit var loginButton : Button

    // Data
    private lateinit var db: FirebaseFirestore
    var sharedData = SharedData.getInstance()
    var uuid = sharedData.getMainUserUUID()

    // TODO : Nice text

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(checkLoggedInt()){
            loadDataAndStart()
        } else {
            displayLogin()
        }
    }

    /**
     * Queries the database to get all the necessary data and starts the main app
     */
    private fun loadDataAndStart() {
        val auth = FirebaseAuth.getInstance()
        sharedData.setTempUUID(auth.currentUser?.uid.toString())
        uuid = sharedData.getMainUserUUID()
        getGames()
        getUserData()
        val intent = Intent(this, CoreApp::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }

    /**
     * Displays the view that allows the user to log in
     */
    private fun displayLogin() {
        setContentView(R.layout.activity_landing)
        loginButton = findViewById(R.id.landing_connect_button)
        val intent = Intent(this, Login::class.java)
        loginButton.setOnClickListener {
            startActivity(intent)
            finish()
        }
    }

    /**
     * Queries the database to get the games and their images
     */
    private fun getGames() {
        db = Firebase.firestore
        val supportedGames = ArrayList<Game>()
        db.collection("Games")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tmp = document.toObject<Game>()
                    tmp.id = document.id
                    supportedGames.add(tmp)
                }
                sharedData.setGames(supportedGames)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    /**
     * Gets all the user's data
     */
    private fun getUserData() {
        val docRef = db.collection("Users").document(uuid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = User()
                user.name = document.data?.get("name") as String
                user.desc = document.data?.get("desc") as String

                val tmp1 = ArrayList<String>()
                for (doc in document.data?.get("likes") as ArrayList<DocumentReference>)
                {
                    tmp1.add(doc.path)
                }
                user.likes = tmp1

                val tmp2 = ArrayList<String>()
                for (doc in document.data?.get("dislikes") as ArrayList<DocumentReference>)
                {
                    tmp2.add(doc.path)
                }
                user.dislikes = tmp2

                val tmp3 = ArrayList<String>()
                for (doc in document.data?.get("games") as ArrayList<DocumentReference>)
                {
                    tmp3.add(doc.path)
                }
                user.games = tmp3

                user.imageURL = document.data?.get("imageURL") as String
                user.uid = document.id
                sharedData.setMainUser(user)
            }
            .addOnFailureListener { exception ->
                println("get failed with $exception")
            }
    }

    /**
     * Checks if the user is logged in
     */
    private fun checkLoggedInt() : Boolean {
        return GoogleSignIn.getLastSignedInAccount(this) != null
    }



}