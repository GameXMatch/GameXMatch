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

class Landing : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    var sharedData = SharedData.getInstance()
    // TODO : Check for credentials
    // TODO : Nice text
    private lateinit var loginButton : Button
    var uuid = sharedData.getMainUserUUID()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(checkLoggedInt()){
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

    fun getUserData() {
        val docRef = db.collection("Users").document(uuid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                user?.uid = document.id
                user?.let { sharedData.setMainUser(it) }

                for (like in document.data?.get("likes") as ArrayList<DocumentReference>) {
                    like.get().addOnSuccessListener{ document1 ->
                        if ((document1.data?.get("likes") as ArrayList<DocumentReference>).contains(db.document("/Users/" + document.id))) {
                            document1.toObject<User>()?.let { sharedData.addMatch(it) }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("get failed with $exception")
            }
    }

    fun checkLoggedInt() : Boolean {
        // TODO
        return GoogleSignIn.getLastSignedInAccount(this) != null
    }



}