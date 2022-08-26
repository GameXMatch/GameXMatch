package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R

class Match : Fragment() {
    // TODO : Data type for the matches
    // TODO : Adaptor for adding items to the list (Big)
    // TODO : Click listener for the items, that will open the individual chat
    lateinit var recycleView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_match, container, false)
        recycleView = view.findViewById(R.id.match_recycleView)

        return view
    }


}