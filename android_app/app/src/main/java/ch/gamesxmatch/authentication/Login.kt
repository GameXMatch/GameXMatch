package ch.gamesxmatch.authentication


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Activity handling the login. Heavily inspired by :
 * https://www.geeksforgeeks.org/google-signing-using-firebase-authentication-in-kotlin/
 * Note : the added/modified functions are located on the bottom
 *
 * This activity transitions to :
 *  - The landing page on successful log-in (Landing)
 */
class Login : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    private val sharedData = SharedData.getInstance()
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        Signin.setOnClickListener { view: View? ->
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    // onActivityResult() function : this is where
    // we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            sharedData.setTempUUID(firebaseAuth.currentUser?.uid.toString())
            val intent = Intent(this, Landing::class.java)
            startActivity(intent)
            finish()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                handleLogIn()
            }
        }
    }

    /**
     * Queries the database to check if the user exists, adds the user if not
     * Note : default delay was set at 1 second to ensure that the data gets downloaded in time
     * Very janky, but it works for the scope of this project
     */
    private fun handleLogIn() {
        sharedData.setTempUUID(firebaseAuth.currentUser?.uid.toString())
        val docRef = db.collection("Users").document(sharedData.getMainUserUUID())

        docRef.get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    addUser()
                }
            }
            .addOnFailureListener { exception ->
                println("get failed with $exception")
            }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Landing::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }


    /**
     * Creates the user in the database
     */
    private fun addUser() {
        sharedData.setTempUUID(firebaseAuth.currentUser?.uid.toString())
        val db = Firebase.firestore
        val user = hashMapOf(
            "name" to firebaseAuth.currentUser?.displayName,
            "desc" to "",
            "imageURL" to firebaseAuth.currentUser?.photoUrl,
            "games" to ArrayList<String>(),
            "likes" to ArrayList<String>(),
            "dislikes" to ArrayList<String>()
        )

        db.collection("Users").document(sharedData.getMainUserUUID())
            .set(user)
            .addOnSuccessListener { println("DocumentSnapshot successfully written!") }
            .addOnFailureListener { println("Error writing document") }
    }
}