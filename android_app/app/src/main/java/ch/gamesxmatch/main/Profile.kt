package ch.gamesxmatch.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.GameListAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData


class Profile: Fragment() {

    lateinit var nameButton: ImageButton
    lateinit var descriptionButton: ImageButton
    lateinit var userNameEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var gameSelectButton: Button
    lateinit var gameDisplayRecyclerView: RecyclerView
    lateinit var gameListAdapter : GameListAdaptator
    val sharedData = SharedData.getInstance()
    val mainUser = sharedData.getMainUser()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view, inflater)
        displayUserData(view, inflater)
        return view
    }


    private fun initViews(view : View, inflater: LayoutInflater) {
        initUsernameViews(view)
        initDescriptionViews(view)
        gameSelectButton = view.findViewById(R.id.profile_button_addGame)
        gameSelectButton.setOnClickListener{
            val intent = Intent(inflater.context, GameSelect::class.java)
            startActivity(intent)
        }
        gameDisplayRecyclerView = view.findViewById(R.id.profile_game_list_recyclerView)
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

    private fun displayUserData(view : View, inflater: LayoutInflater) {
        userNameEditText.setText(mainUser.name)
        descriptionEditText.setText(mainUser.description)
        initGameList(view, inflater)
    }

    private fun initGameList(view : View, inflater: LayoutInflater){
        gameListAdapter = GameListAdaptator(sharedData.getInterestedGames(mainUser))
        gameDisplayRecyclerView.layoutManager = GridLayoutManager(inflater.context, 3)
        gameDisplayRecyclerView.adapter = gameListAdapter
    }


}