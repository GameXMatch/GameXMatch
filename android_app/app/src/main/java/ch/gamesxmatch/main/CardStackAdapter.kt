package ch.gamesxmatch.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R

class CardStackAdapter(val profiles : ArrayList<String>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        bindValues(profiles[position], holder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message : TextView = itemView.findViewById(R.id.message_message)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    private fun bindValues(data : String, holder : ViewHolder) {

    }
}