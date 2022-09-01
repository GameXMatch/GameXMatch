package ch.gamesxmatch.main

import android.graphics.Color
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R

open class GameListAdapter(val game : ArrayList<String>, private val listener: Boolean = false)
    : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    companion object {
        const val selectedColor = "#add8e6"
    }

    override fun getItemCount(): Int {
        return game.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.component_game_list, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindValues(game[position], holder)
    }

    open inner class ViewHolder(private val itemView: View, listener: Boolean) : RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.game_name)
        init {
            if(listener) {
                onGameClicked()
            }
        }

        private fun onGameClicked() {
            itemView.setOnClickListener {
                onItemClick?.invoke(game[adapterPosition])
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

        private fun removeGame() {
            itemView.background = null
            // TODO REQUEST
        }

        private fun addGame() {
            itemView.setBackgroundColor(Color.parseColor(selectedColor))
            // TODO REQUEST
        }
    }

    private fun bindValues(data: String, holder: ViewHolder) {
        holder.message.setText(data)
    }
}
