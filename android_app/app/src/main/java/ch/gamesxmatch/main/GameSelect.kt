package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import java.util.*

class GameSelect: AppCompatActivity() {

    lateinit var returnButton : ImageButton
    lateinit var gameListDisplay : RecyclerView
    val games = ArrayList<String>(Arrays.asList("test1", "test2", "test3", "test4", "test5"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_select)
        returnButton = findViewById(R.id.gameSelect_imageButton)
        gameListDisplay = findViewById(R.id.gameSelect_RecyclerView)
        val gameListAdapter = GameListAdapter(games)
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