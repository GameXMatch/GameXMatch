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

class MatchProfile: AppCompatActivity() {
    lateinit var usernameTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var userImageImageView: ImageView
    lateinit var returnButton: ImageButton
    lateinit var gameListRecyclerView: RecyclerView
    val games = SharedData.getInstance()

    var mainUser = SharedData.getInstance()
    lateinit var chatUser : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_profile)
        usernameTextView = findViewById(R.id.matchProfile_username)
        descriptionTextView = findViewById(R.id.matchProfile_description)
        userImageImageView = findViewById(R.id.matchprofile_profilePicture_imageView)
        returnButton = findViewById(R.id.matchProfile_return_button)
        gameListRecyclerView = findViewById(R.id.matchProfile_game_list_recyclerView)

        val gameListAdapter = GameListAdaptator(games.getGames())
        gameListRecyclerView.layoutManager = GridLayoutManager(this, 3)
        gameListRecyclerView.adapter = gameListAdapter

        getMatchData()

        returnButton.setOnClickListener{
            finish()
        }
    }

    private fun getMatchData(){
        // Data from the match activity
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getInt("matchID")
            chatUser = mainUser.getMatches()[value]
            usernameTextView.setText(chatUser.name)
        }

        // TODO : Get messages and all the needed data
    }

}