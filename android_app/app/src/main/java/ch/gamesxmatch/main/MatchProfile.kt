package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import java.util.*
import kotlin.collections.ArrayList

class MatchProfile: AppCompatActivity() {
    lateinit var usernameTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var userImageImageView: ImageView
    lateinit var returnButton: ImageButton
    lateinit var gameListRecyclerView: RecyclerView
    val games = ArrayList<String>(Arrays.asList("test1", "test2", "test3", "test4", "test5"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_profile)
        usernameTextView = findViewById(R.id.matchProfile_username)
        descriptionTextView = findViewById(R.id.matchProfile_description)
        userImageImageView = findViewById(R.id.matchprofile_profilePicture_imageView)
        returnButton = findViewById(R.id.matchProfile_return_button)
        gameListRecyclerView = findViewById(R.id.matchProfile_game_list_recyclerView)

        val gameListAdapter = GameListAdapter(games)
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
            val value = extras.getString("matchID")
            usernameTextView.setText(value)
        }

        // TODO : Get messages and all the needed data
    }

}