package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ch.gamesxmatch.R

class MatchProfile: AppCompatActivity() {
    lateinit var usernameTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var userImageImageView: ImageView
    lateinit var returnButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_profile)
        usernameTextView = findViewById(R.id.matchProfile_username)
        descriptionTextView = findViewById(R.id.matchProfile_description)
        userImageImageView = findViewById(R.id.matchprofile_profilePicture_imageView)
        returnButton = findViewById(R.id.matchProfile_return_button)

        returnButton.setOnClickListener{
            finish()
        }
    }

}