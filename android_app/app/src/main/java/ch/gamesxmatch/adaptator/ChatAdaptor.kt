package ch.gamesxmatch.adaptator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.data.Message

/**
 * Custom adaptor for chat messages. Makes sure to display the messages appropriately depending
 * on who sent the message
 */
class ChatAdaptor(private val messages : ArrayList<Message>, private val currentUser: String)
    : RecyclerView.Adapter<ChatAdaptor.ViewHolder>() {

    companion object{
        const val margin_default = 50
        const val margin_side = margin_default + 100
        const val background_match = "#add8e6"
        const val background_user = "#A4C639"
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

    /**
     * Initialises the components
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message : TextView = itemView.findViewById(R.id.message_message)
    }

    /**
     * Binds the values for every individual message. Sets the margin, background color and
     * the message itself, depending on who sent the message
     */
    private fun bindValues(data: Message, holder: ViewHolder) {
        if(isMessageFromCurrentUser(data)){
            setMargins(margin_side, margin_default, holder.message)
            holder.message.setBackgroundColor(Color.parseColor(background_match))
        }
        else{
            setMargins(margin_default, margin_side, holder.message)
            holder.message.setBackgroundColor(Color.parseColor(background_user))
        }
        holder.message.setText(data.message)
    }

    /**
     * Sets the margin for the message
     */
    private fun setMargins(left : Int, right : Int, message : TextView) {
        val param = message.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(left,0,right,0)
        message.layoutParams = param
    }

    /**
     * Checks if the message was sent by the current user
     */
    private fun isMessageFromCurrentUser(data: Message) : Boolean {
        return currentUser == data.name
    }

    /**
     * Adds a message to the list and refreshes the component
     */
    fun update(newMessage : Message) {
        messages.add(newMessage)
        this.notifyItemInserted(messages.size)
    }
}