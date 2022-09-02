package ch.gamesxmatch.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import ch.gamesxmatch.authentication.Login
import java.util.*


class Profile: Fragment() {

    lateinit var nameButton: ImageButton
    lateinit var descriptionButton: ImageButton
    lateinit var userNameEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var gameSelectButton: Button
    lateinit var gameDisplayRecyclerView: RecyclerView
    val games = Images.getInstance()

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
        gameSelectButton = view.findViewById(R.id.profile_button_addGame)
        gameSelectButton.setOnClickListener{
            val intent = Intent(inflater.context, GameSelect::class.java)
            startActivity(intent)
        }
    }


    private fun initGameList(view : View, inflater: LayoutInflater){
        gameDisplayRecyclerView = view.findViewById(R.id.profile_game_list_recyclerView)
        val gameListAdapter = GameListAdapter(games.getGames())
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

    private fun addGame(itemPosition : Int){
        //println(games[itemPosition])
        // TODO
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