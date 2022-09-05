package ch.gamesxmatch.adaptator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User

class SwipeAdapter(val user: ArrayList<User>) : RecyclerView.Adapter<SwipeAdapter.ViewHolder>() {

    var sharedData = SharedData.getInstance()
    lateinit var parent: ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.parent = parent
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_swipe_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindValues(user[position], holder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileName: TextView = itemView.findViewById(R.id.idProfileName)
        var profileDescription: TextView = itemView.findViewById(R.id.idProfileDescription)
        var gameList : RecyclerView = itemView.findViewById(R.id.swipe_profile_recyclerview)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    private fun bindValues(data: User, holder: ViewHolder) {
        holder.profileName.text = data.name
        holder.profileDescription.text = data.desc
        holder.gameList.layoutManager = GridLayoutManager(parent.context, 4)
        holder.gameList.adapter = GameListAdaptator(sharedData.getInterestedGames(data))
    }
}