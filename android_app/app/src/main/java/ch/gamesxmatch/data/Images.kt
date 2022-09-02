package ch.gamesxmatch.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class Images private constructor() {
    var imagesTreated = false
    companion object {
        @Volatile
        private lateinit var instance: Images

        val gameImages = HashMap<String, Bitmap>()
        lateinit var user : Bitmap
        val matches = HashMap<String, Bitmap>()

        fun getInstance(): Images {
            synchronized(this) {
                if (!Companion::instance.isInitialized) {
                    instance = Images()
                }
                return instance
            }
        }

    }

    fun setGameImages(links : HashMap<String, String>){
        imagesTreated = false
        for((key, value) in links) {
            println(value)
            val decodedString: ByteArray = Base64.decode(value, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            gameImages.put(key, decodedByte)
            println("test")
        }
        imagesTreated = true
    }

    fun getAllGames() : ArrayList<Bitmap>{
        val games = ArrayList<Bitmap>()
        for((key, value) in gameImages){
            games.add(value)
        }
        return games
    }

    fun getGames() : HashMap<String, Bitmap> {
        return gameImages
    }

}