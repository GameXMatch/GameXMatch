package ch.gamesxmatch.data
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

/**
 * Simple class stocking the data of one user
 */
@IgnoreExtraProperties
data class User(
    var name : String = "",
    var uid : String = "",
    var desc : String = "",
    var games: ArrayList<String> = ArrayList(),
    var likes: ArrayList<String> = ArrayList(),
    var dislikes: ArrayList<String> = ArrayList(),
    var imageURL : String? = null
) : Serializable {
    fun addLike(userId: String) {
        likes.add(userId)
    }

    fun addDislike(userId: String) {
        dislikes.add(userId)
    }
}