package ch.gamesxmatch.data

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentReference

data class User (
    var name : String = "",
    var uid : String = "",
    var desc : String = "",
    val games: ArrayList<DocumentReference> = ArrayList<DocumentReference> (),
    val likes: ArrayList<DocumentReference> = ArrayList<DocumentReference> (),
    val dislikes: ArrayList<DocumentReference> = ArrayList<DocumentReference> (),
    var imageURL : String? = null,
    var image : Bitmap? = null
)