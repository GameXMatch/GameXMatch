package ch.gamesxmatch.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.GameListAdaptor
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData

/**
 * Activity displaying all the games, allowing
 *
 * This activity transitions to :
 *  - The last activity that was active before arriving here. This activity should be the
 *    user's profile (Profile)
 *  Note: The logic behind the click of the game is located in the listener itself (GameListAdaptator)
 */
class GameSelect: AppCompatActivity() {

    // Components
    lateinit var returnButton : ImageButton
    lateinit var gameListDisplay : RecyclerView

    // Data
    val games = SharedData.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
    }

    /**
     * Initialises the components of the view and sets the different data
     */
    private fun initComponents() {
        setContentView(R.layout.activity_game_select)
        returnButton = findViewById(R.id.gameSelect_imageButton)
        gameListDisplay = findViewById(R.id.gameSelect_RecyclerView)
        val gameListAdapter = GameListAdaptor(games.getGames(), true)
        gameListDisplay.layoutManager = GridLayoutManager(this, 3)
        gameListDisplay.adapter = gameListAdapter

        returnButton.setOnClickListener{
            val intent = Intent(this, CoreApp::class.java)
            startActivity(intent)
            finish()
        }
    }

}