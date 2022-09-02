package ch.gamesxmatch.adaptator

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.data.Game

open class GameListAdaptator(val games : ArrayList<Game>, private val listener: Boolean = false)
    : RecyclerView.Adapter<GameListAdaptator.ViewHolder>() {


    var onItemClick: ((String) -> Unit)? = null

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
        init {
            if(listener) {
                updateData()
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
            if(itemView.background == null){
                addGame()
            }
            else {
                removeGame()
            }
        }

        private fun updateData(){
            // TODO UPDATE NAME AND IMAGE
            if(isTheUserInterestedInGame()){
                itemView.setBackgroundColor(Color.parseColor(selectedColor))
            }
        }

        private fun isTheUserInterestedInGame() : Boolean{
            // TODO
            return false
        }

        private fun removeGame() {
            itemView.background = null
            // TODO REQUEST
        }

        private fun addGame() {
            itemView.setBackgroundColor(Color.parseColor(selectedColor))
            // TODO REQUEST
        }
    }

    private fun bindValues(index: Int, holder: ViewHolder) {
        holder.message.setText(games[index].name)
        holder.image.setImageBitmap(games[index].image)
    }
}
