package ch.gamesxmatch.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

class Images private constructor() {
    companion object {
        @Volatile
        private lateinit var instance: Images

        val gameImages = HashMap<String, Bitmap>()
        lateinit var user : Bitmap
        val matches = HashMap<String, Bitmap>()

        fun getInstance(): Images {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = Images()
                }
                return instance
            }
        }

    }

    fun setGameImages(links : HashMap<String, String>){
        for((key, value) in links) {
            val url = URL(value)
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            gameImages.put(key, bmp)
        }
    }

}