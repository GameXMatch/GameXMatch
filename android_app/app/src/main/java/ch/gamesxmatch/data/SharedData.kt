package ch.gamesxmatch.data

import android.graphics.Bitmap


class SharedData private constructor() {
    companion object {
        @Volatile
        private lateinit var instance: SharedData

        val games = ArrayList<Game>()
        lateinit var user : User
        val matches = ArrayList<User> ()

        fun getInstance(): SharedData {
            synchronized(this) {
                if (!Companion::instance.isInitialized) {
                    instance = SharedData()
                }
                return instance
            }
        }

    }

    fun setGames(gameList : ArrayList<Game>){
        games.addAll(gameList)
        for(game in games){
            game.convertStringToBitmap()
        }
    }

    fun getGames() : ArrayList<Game> {
        return games
    }

}