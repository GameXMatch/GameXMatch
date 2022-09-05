package ch.gamesxmatch.data
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var name : String = "",
    var uid : String = "",
    var desc : String = "",
    val games: ArrayList<DocumentReference> = ArrayList<DocumentReference> (),
    val likes: ArrayList<DocumentReference> = ArrayList<DocumentReference> (),
    val dislikes: ArrayList<DocumentReference> = ArrayList<DocumentReference> (),
    var imageURL : String? = null
)