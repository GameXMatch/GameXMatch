package ch.gamesxmatch.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class Game {
    var id : String = ""
    var name : String = ""
    var image : Bitmap? = null
    private var imageData : String = ""

    fun setImageBase64(data : String){
        imageData = data
    }

    fun convertStringToBitmap(){
        if(imageData != "") {
            val decodedString: ByteArray = Base64.decode(imageData, Base64.DEFAULT)
            image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            imageData = ""
        }
    }
}