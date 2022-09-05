package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.MatchAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import java.util.Arrays.asList
import kotlin.collections.ArrayList

class Match : Fragment() {
    // TODO : Data type for the matches
    lateinit var recycleView : RecyclerView
    val mainUser = SharedData.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)
        recycleView = view.findViewById(R.id.match_recycleView)
        val matchAdaptator = MatchAdaptator(mainUser.getMatches())
        recycleView.layoutManager = LinearLayoutManager(inflater.context)
        recycleView.adapter = matchAdaptator


        return view
    }


}