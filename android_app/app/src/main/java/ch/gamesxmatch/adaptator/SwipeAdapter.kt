package ch.gamesxmatch.adaptator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.data.User

class SwipeAdapter(val user: ArrayList<User>) : RecyclerView.Adapter<SwipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_swipe_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindValues(user[position], holder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileName: TextView = itemView.findViewById(R.id.idProfileName)
        var profileDescription: TextView = itemView.findViewById(R.id.idProfileDescription)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    private fun bindValues(data: User, holder: ViewHolder) {
        holder.profileName.text = data.name
        holder.profileDescription.text = data.desc
    }
}