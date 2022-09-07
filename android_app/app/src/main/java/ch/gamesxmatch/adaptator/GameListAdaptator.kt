package ch.gamesxmatch.adaptator

import android.graphics.Bitmap
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

open class GameListAdaptator(val games : ArrayList<Game>, private val listener: Boolean = false)
    : RecyclerView.Adapter<GameListAdaptator.ViewHolder>() {

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

    open inner class ViewHolder(private val itemView: View, listener: Boolean) : RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.game_name)
        var image : ImageView = itemView.findViewById(R.id.game_imageView)
        var uuid : TextView = itemView.findViewById(R.id.game_uuid)
        var layout : LinearLayout = itemView.findViewById(R.id.game_layout)
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
                var game = FirebaseFirestore.getInstance().document(gameuuid)
                if(game.id == uuid.text.toString()){
                    mainUser.games.remove(gameuuid)
                    break
                }
            }

            // Request
            println(uuid.text.toString())
            val uRef = db.collection("Users").document(sharedData.getMainUserUUID())
            uRef.update("games", FieldValue.arrayRemove(db.document("/Games/" + uuid.text.toString())))
                .addOnSuccessListener { println("DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> println("Error updating document $e") }
        }

        private fun addGame() {
            layout.setBackgroundColor(Color.parseColor(selectedColor))

            for (gameuuid in mainUser.games) {
                var game = FirebaseFirestore.getInstance().document(gameuuid)
                if(game.id == uuid.text.toString()){
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

    private fun isTheUserInterestedInGame(holder: ViewHolder) : Boolean{
        val game = holder.uuid.text.toString()
        for(userGame in mainUser.games){
            var dbGame = FirebaseFirestore.getInstance().document(userGame)
            if(dbGame.id == game){
                return true
            }
        }

        return false
    }
}
