package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import ch.gamesxmatch.R

class Swipe : Fragment() {

    // TODO : Create data type for the match
    // TODO : Find a way to display the data
    // TODO : Implement the swipe logic and refresh of the card
    // Maybe find something better than a Cardview?

    lateinit var btnSwipeLeft : Button
    lateinit var btnSwipeRight : Button
    lateinit var cardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe, container, false)
        btnSwipeLeft = view.findViewById(R.id.swipe_left_button)
        btnSwipeRight = view.findViewById(R.id.swipe_right_button)
        cardView = view.findViewById(R.id.swipe_cardview)
        return view
    }
}