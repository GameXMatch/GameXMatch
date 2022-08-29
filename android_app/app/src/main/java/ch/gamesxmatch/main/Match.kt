package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import java.util.Arrays.asList
import kotlin.collections.ArrayList

class Match : Fragment() {
    // TODO : Data type for the matches
    lateinit var recycleView : RecyclerView
    var matches = ArrayList<String>(asList("test1", "test2", "test3", "test4", "test5"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)
        recycleView = view.findViewById(R.id.match_recycleView)
        val matchAdaptator = MatchAdaptator(matches)
        recycleView.layoutManager = LinearLayoutManager(inflater.context)
        recycleView.adapter = matchAdaptator


        return view
    }


}