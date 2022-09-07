package ch.gamesxmatch.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.GameListAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.authentication.Landing
import ch.gamesxmatch.data.SharedData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

/**
 * Activity displaying the profile of the current user.
 * This activity allows to :
 *  - Display user information, including profile picture, interested games, user name and description
 *  - Edit the user name
 *  - Edit the user's description
 *  - Open the activity, allowing to add or delete games to the profile
 *  - Log out
 *
 * This activity transitions to :
 *  - The activity allowing the user to select their games (GameSelect)
 *  - The landing page in case of a log out (Landing)
 */
class Profile: Fragment() {

    // Components
    lateinit var nameButton: ImageButton
    lateinit var descriptionButton: ImageButton
    lateinit var userNameEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var gameSelectButton: Button
    lateinit var gameDisplayRecyclerView: RecyclerView
    lateinit var gameListAdapter : GameListAdaptator
    lateinit var logoutButton : Button
    lateinit var profileImageView : ImageView

    // Data for components
    lateinit var parentView : View
    lateinit var parentContext: Context

    // Data
    val sharedData = SharedData.getInstance()
    val mainUser = sharedData.getMainUser()
    val userGames = sharedData.getInterestedGames(mainUser)
    private lateinit var db: FirebaseFirestore

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = Firebase.firestore
        parentView = inflater.inflate(R.layout.fragment_profile, container, false)
        parentContext = inflater.context
        initComponents()
        return parentView
    }

    /**
     * Initialises the different components of the view
     */
    private fun initComponents() {
        initProfileImageView()
        initUsernameViews()
        initDescriptionViews()
        initGameDisplay()
        initGameSelect()
        initLogOutButton()
    }

    /**
     * Initialises the imageView for the profile picture, updates it with user's profile picture
     */
    private fun initProfileImageView() {
        profileImageView = parentView.findViewById(R.id.profile_profilePicture_imageView)
        Picasso.with(parentContext).load(sharedData.getMainUser().imageURL).into(profileImageView)
    }

    /**
     * Initialises the components and logic behind handling the edit of the user name
     * Sets the current user's name
     */
    private fun initUsernameViews() {
        userNameEditText = parentView.findViewById(R.id.profile_username_editText)
        userNameEditText.isEnabled = false
        nameButton = parentView.findViewById(R.id.profile_nameEdit_imageButton)
        userNameEditText.setText(mainUser.name)

        nameButton.setOnClickListener{
            handleUsernameButtonClick()
        }
    }

    /**
     * Initialises the components and logic behind handling the edit of the description
     * Sets the current description
     */
    private fun initDescriptionViews(){
        descriptionEditText = parentView.findViewById(R.id.profile_description_editText)
        descriptionEditText.isEnabled = false
        descriptionButton = parentView.findViewById(R.id.profile_description_imageButton)
        descriptionEditText.setText(mainUser.desc)

        descriptionButton.setOnClickListener{
            handleDescriptionButtonClick()
        }
    }

    /**
     * Initialises the different parameters needed for the display of the games the user
     * is interested in
     */
    private fun initGameDisplay() {
        gameDisplayRecyclerView = parentView.findViewById(R.id.profile_game_list_recyclerView)
        gameListAdapter = GameListAdaptator(userGames)
        gameDisplayRecyclerView.layoutManager = GridLayoutManager(parentContext, 3)
        gameDisplayRecyclerView.adapter = gameListAdapter
    }

    /**
     * Initialises the button allowing to transition
     */
    private fun initGameSelect() {
        gameSelectButton = parentView.findViewById(R.id.profile_button_addGame)
        gameSelectButton.setOnClickListener{
            val intent = Intent(parentContext, GameSelect::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    /**
     * Initialises the button allowing the user to log out
     */
    private fun initLogOutButton() {
        logoutButton = parentView.findViewById(R.id.profile_log_out_button)
        logoutButton.setOnClickListener{
            logOut()
        }
    }


    /**
     * Function handling the click of the button that updates the user's name
     * This function does the following :
     *  - If the editText is enabled
     *      - Will query the content in the database for the update
     *      - Will disable the editText for modification and will change the icon to imply
     *        that clicking the button will allow editing
     *  - Otherwise, it will enable the editText and change the icon to send
     */
    private fun handleUsernameButtonClick() {
        if(userNameEditText.isEnabled){
            updateUsername()
            nameButton.setImageResource(android.R.drawable.ic_menu_edit)
        } else {
            nameButton.setImageResource(android.R.drawable.ic_media_play)
        }
        userNameEditText.isEnabled = !userNameEditText.isEnabled
    }

    /**
     * Function handling the click of the button that updates the user's description
     * This function does the following :
     *  - If the editText is enabled
     *      - Will query the content in the database for the update
     *      - Will disable the editText for modification and will change the icon to imply
     *        that clicking the button will allow editing
     *  - Otherwise, it will enable the editText and change the icon to send
     */
    private fun handleDescriptionButtonClick() {
        if(descriptionEditText.isEnabled){
            updateDescription()
            descriptionButton.setImageResource(android.R.drawable.ic_menu_edit)
        } else {
            descriptionButton.setImageResource(android.R.drawable.ic_media_play)

        }
        descriptionEditText.isEnabled = !descriptionEditText.isEnabled
    }

    /**
     * Gets the data from the userNameEditText and queries it to the database, to update the
     * user's name
     */
    private fun updateUsername() {
        val username = userNameEditText.text.toString()

        val uRef = db.collection("Users").document(sharedData.getMainUserUUID())

        uRef.update("name", username)
            .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> println("Error updating document $e") }
    }

    /**
     * Gets the data from the descriptionEditText and queries it to the database, to update the
     * user's description
     */
    private fun updateDescription() {
        val description = descriptionEditText.text.toString()
        val uRef = db.collection("Users").document(sharedData.getMainUserUUID())

        uRef.update("desc", description)
            .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> println("Error updating document $e") }
    }

    /**
     * Invalidates the user's credentials by signing out and redirects the app to the
     * Landing activity
     */
    private fun logOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        var mGoogleSignInClient = GoogleSignIn.getClient(parentContext, gso)
        mGoogleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(parentContext, Landing::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}