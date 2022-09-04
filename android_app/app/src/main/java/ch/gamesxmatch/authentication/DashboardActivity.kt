package ch.gamesxmatch.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    // declare the GoogleSignInClient
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val sharedData = SharedData.getInstance()
    private var canProceed = false
    private lateinit var db: FirebaseFirestore

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        db = Firebase.firestore

        // call requestIdToken as follows
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sharedData.setTempUUID(auth.currentUser?.uid.toString())
        handleUser()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Landing::class.java)
            startActivity(intent)
            finish()
        }, 1000)

        logout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, Login::class.java)
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }
    }

    private fun handleUser() {

        println(sharedData.getMainUserUUID())
        val docRef = db.collection("Users").document(sharedData.getMainUserUUID())
        docRef.get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    addUser()
                    println("ahoy")
                }
                println("ahoy2")
            }
            .addOnFailureListener { exception ->
                 println("get failed with $exception")
            }

    }

    private fun addUser() {
        val user = hashMapOf(
            "name" to auth.currentUser?.displayName,
            "desc" to "",
            "imageURL" to auth.currentUser?.photoUrl,
            "games" to ArrayList<DocumentReference>(),
            "likes" to ArrayList<DocumentReference>(),
            "dislikes" to ArrayList<DocumentReference>()
        )

        db.collection("Users").document(sharedData.getMainUserUUID())
            .set(user)
            .addOnSuccessListener { println("DocumentSnapshot successfully written!") }
            .addOnFailureListener { println("Error writing document") }
    }
}