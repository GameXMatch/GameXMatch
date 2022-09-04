package ch.gamesxmatch.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.data.User

class SwipeAdapter(val profiles: ArrayList<User>) : RecyclerView.Adapter<SwipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_swipe_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SwipeAdapter.ViewHolder, position: Int) {
        bindValues(profiles[position], holder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileName: TextView = itemView.findViewById(R.id.idProfileName)
        var profileDescription: TextView = itemView.findViewById(R.id.idProfileDescription)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    private fun bindValues(data: User, holder: ViewHolder) {
        holder.profileName.text = data.name
        holder.profileDescription.text = data.desc
    }
}