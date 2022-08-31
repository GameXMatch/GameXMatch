package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import java.util.*


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
        initSpinner(view, inflater)
    }

    private fun initSpinner(view: View, inflater: LayoutInflater){
        gameSelectSpinner = view.findViewById(R.id.profile_game_selection_spinner)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            inflater.context,
            android.R.layout.simple_spinner_item, games
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gameSelectSpinner.setAdapter(adapter)

        gameSelectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                addGame(adapterView.selectedItemPosition)
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }
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

    private fun addGame(itemPosition : Int){
        println(games[itemPosition])
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