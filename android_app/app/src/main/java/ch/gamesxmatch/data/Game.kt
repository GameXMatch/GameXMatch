package ch.gamesxmatch.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

data class Game(
    var id : String = "",
    var name : String = "",
    var image : Bitmap? = null,
    var imageURL : String = ""
) {

    fun convertStringToBitmap(){
        if(imageURL != "") {
            val decodedString: ByteArray = Base64.decode(imageURL, Base64.DEFAULT)
            image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            imageURL = ""
        }
    }
}