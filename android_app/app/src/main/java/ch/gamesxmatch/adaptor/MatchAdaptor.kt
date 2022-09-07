package ch.gamesxmatch.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.data.User
import ch.gamesxmatch.main.Chat
import com.squareup.picasso.Picasso

/**
 * Adaptor for the list of matches. Displays all the matches with the user and handles the click,
 * allowing the user to be redirected in the chat
 */
class MatchAdaptor(val matches : ArrayList<User>) : RecyclerView.Adapter<MatchAdaptor.ViewHolder>() {

    var onItemClick: ((User) -> Unit)? = null
    lateinit var context: Context

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_individual_match, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindValues(matches[position], holder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNickname : TextView = itemView.findViewById(R.id.message_message)
        var imgAvatar :  ImageView = itemView.findViewById(R.id.match_avatar)
        init {
            openChatOnClickListener(itemView)
        }

        /**
         * Redirects the user to the chat on click
         */
        private fun openChatOnClickListener(itemView: View) {
            itemView.setOnClickListener {
                onItemClick?.invoke(matches[bindingAdapterPosition])
                val intent = Intent(itemView.context, Chat::class.java)

                // TODO : pass the necessary data for the chat
                intent.putExtra("match", matches[bindingAdapterPosition])
                val test = matches[bindingAdapterPosition]

                itemView.context.startActivities(arrayOf(intent))
            }
        }
    }

    /**
     * sets the values in the components
     */
    private fun bindValues(data : User, holder : ViewHolder) {
        holder.txtNickname.setText(data.name)
        Picasso.with(context).load(data.imageURL).into(holder.imgAvatar)
    }

    /**
     * Refreshes the display of the matches on a new added one
     */
    fun update(toUpdate : User) {
        matches.add(toUpdate)
        this.notifyItemInserted(matches.size)
    }
}