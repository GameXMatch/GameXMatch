package ch.gamesxmatch.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R


class MatchAdaptator(val matches : ArrayList<String>) : RecyclerView.Adapter<MatchAdaptator.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_individual_match, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindValues(matches[position], holder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNickname : TextView = itemView.findViewById(R.id.match_nickname)
        var imgAvatar :  ImageView = itemView.findViewById(R.id.match_avatar)
        var imgNotification : ImageView = itemView.findViewById(R.id.match_new_message)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(matches[adapterPosition])
            }
        }
    }

    private fun bindValues(data : String, holder : ViewHolder) {
        holder.txtNickname.setText(data)
    }

}