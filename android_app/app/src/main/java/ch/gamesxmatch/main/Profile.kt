package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import java.util.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList

class Profile: Fragment() {

    lateinit var nameButton: ImageButton
    lateinit var descriptionButton: ImageButton
    lateinit var userNameEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var gameSelectSpinner: Spinner
    lateinit var gameDisplayRecyclerView: RecyclerView
    val games = ArrayList<String>(Arrays.asList("test1", "test2", "test3", "test4", "test5"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view, inflater)
        return view
    }

    private fun initViews(view : View, inflater: LayoutInflater) {
        initUsernameViews(view)
        initDescriptionViews(view)
        initGameList(view, inflater)
        gameSelectSpinner = view.findViewById(R.id.profile_game_selection_spinner)
    }

    private fun initGameList(view : View, inflater: LayoutInflater){
        gameDisplayRecyclerView = view.findViewById(R.id.profile_game_list_recyclerView)
        val gameListAdapter = GameListAdapter(games)
        gameDisplayRecyclerView.layoutManager = GridLayoutManager(inflater.context, 3)
        gameDisplayRecyclerView.adapter = gameListAdapter
    }

    private fun initUsernameViews(view : View){
        userNameEditText = view.findViewById(R.id.profile_username_editText)
        userNameEditText.isEnabled = false
        nameButton = view.findViewById(R.id.profile_nameEdit_imageButton)

        nameButton.setOnClickListener{
            if(userNameEditText.isEnabled){
                updateUsername()
                nameButton.setImageResource(android.R.drawable.ic_menu_edit)
            } else {
                nameButton.setImageResource(android.R.drawable.ic_media_play)
            }
            userNameEditText.isEnabled = !userNameEditText.isEnabled
        }
    }

    private fun initDescriptionViews(view: View){
        descriptionEditText = view.findViewById(R.id.profile_description_editText)
        descriptionEditText.isEnabled = false
        descriptionButton = view.findViewById(R.id.profile_description_imageButton)


        descriptionButton.setOnClickListener{
            if(descriptionEditText.isEnabled){
                updateDescription()
                descriptionButton.setImageResource(android.R.drawable.ic_menu_edit)
            } else {
                descriptionButton.setImageResource(android.R.drawable.ic_media_play)

            }
            descriptionEditText.isEnabled = !descriptionEditText.isEnabled
        }
    }

    private fun updateUsername() {
        val username = userNameEditText.text.toString()
        // TODO : send request
    }

    private fun updateDescription() {
        val description = descriptionEditText.text.toString()
        // TODO : send request
    }

}