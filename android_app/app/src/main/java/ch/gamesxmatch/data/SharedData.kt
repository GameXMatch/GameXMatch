package ch.gamesxmatch.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class SharedData private constructor() {
    companion object {
        @Volatile
        private lateinit var instance: SharedData

        val games = ArrayList<Game>()
        lateinit var user : Bitmap
        val matches = HashMap<String, Bitmap>()

        fun getInstance(): SharedData {
            synchronized(this) {
                if (!Companion::instance.isInitialized) {
                    instance = SharedData()
                }
                return instance
            }
        }

    }

    fun setGameImages(gameList : ArrayList<Game>){
        games.addAll(gameList)
        for(game in games){
            game.convertStringToBitmap()
        }
    }

    fun getGames() : ArrayList<Game> {
        return games
    }

}