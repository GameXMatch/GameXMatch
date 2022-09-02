package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.GameListAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.data.Images

class GameSelect: AppCompatActivity() {

    lateinit var returnButton : ImageButton
    lateinit var gameListDisplay : RecyclerView
    val games = Images.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_select)
        returnButton = findViewById(R.id.gameSelect_imageButton)
        gameListDisplay = findViewById(R.id.gameSelect_RecyclerView)
        val gameListAdapter = GameListAdaptator(games.getGames(), true)
        gameListDisplay.layoutManager = GridLayoutManager(this, 3)
        gameListDisplay.adapter = gameListAdapter

        getData()

        returnButton.setOnClickListener{
            finish()
        }
    }

    private fun getData(){

        // TODO : Get messages and all the needed data
    }

}