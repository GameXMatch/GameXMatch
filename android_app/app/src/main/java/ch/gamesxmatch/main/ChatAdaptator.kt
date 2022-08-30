package ch.gamesxmatch.main

import android.app.ActionBar
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R


class ChatAdaptator(val messages : ArrayList<String>) : RecyclerView.Adapter<ChatAdaptator.ViewHolder>() {

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message : TextView = itemView.findViewById(R.id.message_message)
    }

    private fun bindValues(data : String, holder : ViewHolder) {
        if(matchMessage(data)){
            setMargins(margin_side, margin_default, holder.message)
        }
        else{
            setMargins(margin_default, margin_side, holder.message)
            holder.message.setBackgroundColor(Color.parseColor(background_user))
        }
        holder.message.setText(data)
    }

    private fun setMargins(left : Int, right : Int, message : TextView) {
        val param = message.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(left,0,right,0)
        message.layoutParams = param
    }

    private fun matchMessage(data: String) : Boolean {
        // TODO
        return data[0] == '1'
    }

}