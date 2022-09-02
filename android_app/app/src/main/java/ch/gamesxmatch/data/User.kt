package ch.gamesxmatch.data

import android.graphics.Bitmap

class User {
    var name : String = ""
    var uid : String = ""
    var description : String = ""
    val gamesUIDs = ArrayList<String> ()
    val likes = ArrayList<String> ()
    val dislikes = ArrayList<String> ()
    var image : Bitmap? = null
    var imageData : String = ""

    fun removeGame(game: Game) {
        for (gameuuid in gamesUIDs) {
            if(gameuuid.contains(game.id)){
                gamesUIDs.remove(gameuuid)
            }
        }
    }

    fun removeGame(game: String) {
        for (gameuuid in gamesUIDs) {
            if(gameuuid.contains(game)){
                gamesUIDs.remove(gameuuid)
            }
        }
    }

    fun addGame(game: Game) {
        for (gameuuid in gamesUIDs) {
            if(gameuuid.contains(game.id)){
                return
            }
        }
        gamesUIDs.add(game.id)
    }

    fun addGame(game: String) {
        for (gameuuid in gamesUIDs) {
            if(gameuuid.contains(game)){
                return
            }
        }
        gamesUIDs.add(game)
    }
}