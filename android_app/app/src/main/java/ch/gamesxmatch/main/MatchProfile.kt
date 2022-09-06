package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.GameListAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import com.squareup.picasso.Picasso

class MatchProfile: AppCompatActivity() {
    lateinit var usernameTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var userImageImageView: ImageView
    lateinit var returnButton: ImageButton
    lateinit var gameListRecyclerView: RecyclerView
    val sharedData = SharedData.getInstance()
    var match : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_profile)
        usernameTextView = findViewById(R.id.matchProfile_username)
        descriptionTextView = findViewById(R.id.matchProfile_description)
        userImageImageView = findViewById(R.id.matchprofile_profilePicture_imageView)
        returnButton = findViewById(R.id.matchProfile_return_button)
        gameListRecyclerView = findViewById(R.id.matchProfile_game_list_recyclerView)

        getMatchData()
        if (match != null) {
            val gameListAdapter = GameListAdaptator(sharedData.getInterestedGames(match!!))
            gameListRecyclerView.layoutManager = GridLayoutManager(this, 3)
            gameListRecyclerView.adapter = gameListAdapter
        }


        returnButton.setOnClickListener{
            finish()
        }
    }

    private fun getMatchData(){
        // Data from the match activity
        val extras = intent.extras
        if (extras != null) {
            val value = extras.get("matchID").toString()
            if (value != null) {
                match = sharedData.getMatches()[value.toInt()]
                usernameTextView.setText(match?.name)
                descriptionTextView.setText(match?.desc)
                Picasso.with(this).load(match?.imageURL).into(userImageImageView)
            }

        }
        // TODO : Get messages and all the needed data
    }


}