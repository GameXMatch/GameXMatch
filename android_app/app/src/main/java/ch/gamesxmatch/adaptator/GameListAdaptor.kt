package ch.gamesxmatch.adaptator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.data.Game
import ch.gamesxmatch.data.SharedData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Adaptor of the game list. Is used both as a way to display games in read-only mode, and as a way
 * for the user to add or delete selected games. The listener bool flag is here for selecting
 * which mode to use.
 *
 * The current structure is very confusing, but refactoring it would be very dangerous with the
 * deadline approaching quickly.
 */
open class GameListAdaptor(val games : ArrayList<Game>, private val listener: Boolean = false)
    : RecyclerView.Adapter<GameListAdaptor.ViewHolder>() {

    var sharedData = SharedData.getInstance()
    var mainUser = sharedData.getMainUser()
    var onItemClick: ((String) -> Unit)? = null

    private lateinit var db: FirebaseFirestore

    companion object {
        const val selectedColor = "#add8e6"
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.component_game_list, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindValues(position, holder)
    }

    /**
     * Sets the data on the components
     */
    private fun bindValues(index: Int, holder: ViewHolder) {
        holder.message.setText(games[index].name)
        if(games[index].image != null) {
            holder.image.setImageBitmap(games[index].image)
        }
        holder.uuid.setText(games[index].id)


        if(isTheUserInterestedInGame(holder) && listener){
            holder.layout.setBackgroundColor(Color.parseColor(selectedColor))
        }
    }

    /**
     * Checks if the user already added the game among the interested ones 
     */
    private fun isTheUserInterestedInGame(holder: ViewHolder) : Boolean{
        val game = holder.uuid.text.toString()
        for(userGame in mainUser.games){
            if(userGame.contains(game)){
                return true
            }
        }
        return false
    }

    /**
     * Gets the reference of the different components, and in the case of the listener flag being
     * true, and sets up the logic of the listener
     */
    open inner class ViewHolder(private val itemView: View, listener: Boolean) : RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.game_name)
        var image : ImageView = itemView.findViewById(R.id.game_imageView)
        var uuid : TextView = itemView.findViewById(R.id.game_uuid)
        var layout : LinearLayout = itemView.findViewById(R.id.game_layout)

        // Listener stuff
        init {
            if(listener) {
                db = Firebase.firestore
                onGameClicked()
            }
        }

        private fun onGameClicked() {
            itemView.setOnClickListener {
                onItemClick?.invoke(games[adapterPosition].id)
                updateClickedGame()
            }
        }

        private fun updateClickedGame() {
            if(layout.background == null){
                addGame()
            }
            else {
                removeGame()
            }
        }

        private fun removeGame() {
            layout.background = null

            for (gameuuid in mainUser.games) {
                if(gameuuid.contains(uuid.text.toString())){
                    mainUser.games.remove(gameuuid)
                    break
                }
            }

            // Request
            val uRef = db.collection("Users").document(sharedData.getMainUserUUID())
            uRef.update("games", FieldValue.arrayRemove(db.document("/Games/" + uuid.text.toString())))
                .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> println("Error updating document $e") }
        }

        private fun addGame() {
            layout.setBackgroundColor(Color.parseColor(selectedColor))

            for (gameuuid in mainUser.games) {
                if(gameuuid.contains(uuid.text.toString())){
                    return
                }
            }
            mainUser.games.add("/Games/" + uuid.text.toString() + "/")

            // Request
            val uRef = db.collection("Users").document(sharedData.getMainUserUUID())
            uRef.update("games", FieldValue.arrayUnion(db.document("/Games/" + uuid.text.toString())))
                .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> println("Error updating document $e") }
        }
    }


}
