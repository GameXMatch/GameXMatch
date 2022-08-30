package ch.gamesxmatch.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R


class ChatAdaptator(val messages : ArrayList<String>) : RecyclerView.Adapter<ChatAdaptator.ViewHolder>() {

    companion object{
        const val message_weight = 5
        const val space_weight = 1
        const val space_disabled_weight = 0
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindValues(messages[position], holder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message : TextView = itemView.findViewById(R.id.message_message)
        var leftSpace : Space = itemView.findViewById(R.id.message_left_space)
        var rightSpace : Space = itemView.findViewById(R.id.message_right_space)
    }

    private fun bindValues(data : String, holder : ViewHolder) {
        holder.message.setText(data)
    }



}