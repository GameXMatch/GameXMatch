package ch.gamesxmatch.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ch.gamesxmatch.R

class Landing : AppCompatActivity() {
    private lateinit var loginButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        loginButton = findViewById(R.id.landing_connect_button)
        val intent = Intent(this, Login::class.java)
        loginButton.setOnClickListener{
            startActivity(intent)
        }
    }


}