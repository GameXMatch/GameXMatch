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

        }
    }
}