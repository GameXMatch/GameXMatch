package ch.gamesxmatch.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ch.gamesxmatch.R
import ch.gamesxmatch.main.CoreApp

class Landing : AppCompatActivity() {

    // TODO : Check for credentials
    // TODO : Nice text
    private lateinit var loginButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(checkLoggedInt()){
            val intent = Intent(this, CoreApp::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_landing)
            loginButton = findViewById(R.id.landing_connect_button)
            val intent = Intent(this, Login::class.java)
            loginButton.setOnClickListener {
                startActivity(intent)
            }
        }
    }

    fun checkLoggedInt() : Boolean {
        // TODO
        return false
    }



}