package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptor.GameListAdaptor
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import com.squareup.picasso.Picasso

/**
 * Activity displaying a match's profile and its data in read only.
 *
 * This activity transitions to :
 *  - The last activity that was active before arriving here. This activity should be the
 *    individual chat between the match and the user (Chat)
 */
class MatchProfile: AppCompatActivity() {

    // Components
    lateinit var usernameTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var userImageImageView: ImageView
    lateinit var returnButton: ImageButton
    lateinit var gameListRecyclerView: RecyclerView

    // Data
    val sharedData = SharedData.getInstance()
    var match : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        handleData()
    }

    /**
     * Initialises the different components of activity
     */
    private fun initComponents() {
        setContentView(R.layout.activity_match_profile)
        usernameTextView = findViewById(R.id.matchProfile_username)
        descriptionTextView = findViewById(R.id.matchProfile_description)
        userImageImageView = findViewById(R.id.matchprofile_profilePicture_imageView)
        returnButton = findViewById(R.id.matchProfile_return_button)
        gameListRecyclerView = findViewById(R.id.matchProfile_game_list_recyclerView)

        returnButton.setOnClickListener{
            finish()
        }
    }

    /**
     * Gets the match data, coming from the Chat fragment. It is a stored id, referencing
     * the position in the match list.
     * In the case that the said id doesn't exist, the activity closes
     */
    private fun handleData(){
        val extras = intent.extras
        if (extras != null) {
            match = extras.get("match") as User
            setMatchData()
        }
        else {
            finish()
        }
    }

    /**
     * Sets the different information of the user where it belongs.
     */
    private fun setMatchData() {
        usernameTextView.setText(match?.name)
        descriptionTextView.setText(match?.desc)
        Picasso.with(this).load(match?.imageURL).into(userImageImageView)

        val matchGames = sharedData.getInterestedGames(match!!)
        gameListRecyclerView.layoutManager = GridLayoutManager(this, 3)
        gameListRecyclerView.adapter = GameListAdaptor(matchGames)
    }


}