package ch.gamesxmatch.main

import android.content.Context
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

/**
 * Activity displaying all matches a user has.
 *
 * This activity transitions to :
 *  - The chat between the user and the clicked match (Chat)
 *    NOTE : The transition was implemented in MatchAdaptator
 */
class Match : Fragment() {

    // Components
    lateinit var recycleView : RecyclerView

    // Data for components
    lateinit var parentView : View
    lateinit var parentContext: Context

    // Data
    val mainUser = SharedData.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(R.layout.fragment_match, container, false)
        parentContext = inflater.context

        initComponents()

        return parentView
    }

    /**
     * Initialises the components of the view
     */
    private fun initComponents() {
        recycleView = parentView.findViewById(R.id.match_recycleView)
        val matchAdaptator = MatchAdaptator(mainUser.getMatches())
        recycleView.layoutManager = LinearLayoutManager(parentContext)
        recycleView.adapter = matchAdaptator
    }

}