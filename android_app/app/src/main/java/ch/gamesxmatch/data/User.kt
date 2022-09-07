package ch.gamesxmatch.data
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class User(
    var name : String = "",
    var uid : String = "",
    var desc : String = "",
    var games: ArrayList<String> = ArrayList<String> (),
    var likes: ArrayList<String> = ArrayList<String> (),
    var dislikes: ArrayList<String> = ArrayList<String> (),
    var imageURL : String? = null
) : Serializable {
    fun addLike(userId: String) {
        likes.add(userId)
    }

    fun addDislike(userId: String) {
        dislikes.add(userId)
    }
}