package ch.gamesxmatch.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.GameListAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.authentication.Login
import ch.gamesxmatch.data.SharedData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Profile: Fragment() {

    lateinit var nameButton: ImageButton
    lateinit var descriptionButton: ImageButton
    lateinit var userNameEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var gameSelectButton: Button
    lateinit var gameDisplayRecyclerView: RecyclerView
    lateinit var gameListAdapter : GameListAdaptator
    lateinit var logoutButton : Button
    val sharedData = SharedData.getInstance()
    val mainUser = sharedData.getMainUser()
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = Firebase.firestore
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view, inflater)
        displayUserData(view, inflater)
        return view
    }


    private fun initViews(view : View, inflater: LayoutInflater) {
        initUsernameViews(view)
        initDescriptionViews(view)
        gameSelectButton = view.findViewById(R.id.profile_button_addGame)
        gameSelectButton.setOnClickListener{
            val intent = Intent(inflater.context, GameSelect::class.java)
            startActivity(intent)
        }
        gameDisplayRecyclerView = view.findViewById(R.id.profile_game_list_recyclerView)

        initLogOutButton(view, inflater)
    }

    private fun initLogOutButton(view : View, inflater: LayoutInflater) {
        logoutButton = view.findViewById(R.id.profile_log_out_button)
        logoutButton.setOnClickListener{
            // TODO disconnect
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            var mGoogleSignInClient = GoogleSignIn.getClient(inflater.context, gso)
            mGoogleSignInClient.signOut().addOnCompleteListener {
                activity?.finish()
            }

        }
    }

    private fun initUsernameViews(view : View){
        userNameEditText = view.findViewById(R.id.profile_username_editText)
        userNameEditText.isEnabled = false
        nameButton = view.findViewById(R.id.profile_nameEdit_imageButton)

        nameButton.setOnClickListener{
            if(userNameEditText.isEnabled){
                updateUsername()
                nameButton.setImageResource(android.R.drawable.ic_menu_edit)
            } else {
                nameButton.setImageResource(android.R.drawable.ic_media_play)
            }
            userNameEditText.isEnabled = !userNameEditText.isEnabled
        }
    }

    private fun initDescriptionViews(view: View){
        descriptionEditText = view.findViewById(R.id.profile_description_editText)
        descriptionEditText.isEnabled = false
        descriptionButton = view.findViewById(R.id.profile_description_imageButton)


        descriptionButton.setOnClickListener{
            if(descriptionEditText.isEnabled){
                updateDescription()
                descriptionButton.setImageResource(android.R.drawable.ic_menu_edit)
            } else {
                descriptionButton.setImageResource(android.R.drawable.ic_media_play)

            }
            descriptionEditText.isEnabled = !descriptionEditText.isEnabled
        }
    }


    private fun updateUsername() {
        val username = userNameEditText.text.toString()

        val uRef = db.collection("Users").document(sharedData.getMainUserUUID())

        uRef.update("name", username)
            .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> println("Error updating document $e") }
    }

    private fun updateDescription() {
        val description = descriptionEditText.text.toString()
        val uRef = db.collection("Users").document(sharedData.getMainUserUUID())

        uRef.update("desc", description)
            .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> println("Error updating document $e") }
    }

    private fun displayUserData(view : View, inflater: LayoutInflater) {
        userNameEditText.setText(mainUser.name)
        descriptionEditText.setText(mainUser.desc)
        initGameList(view, inflater)
    }

    private fun initGameList(view : View, inflater: LayoutInflater){
        gameListAdapter = GameListAdaptator(sharedData.getInterestedGames(mainUser))
        gameDisplayRecyclerView.layoutManager = GridLayoutManager(inflater.context, 3)
        gameDisplayRecyclerView.adapter = gameListAdapter
    }


}