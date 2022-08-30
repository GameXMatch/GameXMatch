package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import ch.gamesxmatch.R
import java.util.zip.Inflater

class Profile: Fragment() {

    lateinit var nameButton: ImageButton
    lateinit var descriptionButton: ImageButton
    lateinit var userNameEditText: EditText
    lateinit var descriptionEditText: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view : View) {
        initUsernameViews(view)
        initDescriptionViews(view)
    }

    private fun initUsernameViews(view : View){
        userNameEditText = view.findViewById(R.id.profile_username_editText)
        userNameEditText.isEnabled = false
        nameButton = view.findViewById(R.id.profile_nameEdit_imageButton)

        nameButton.setOnClickListener{
            userNameEditText.isEnabled = !userNameEditText.isEnabled
            if(userNameEditText.isEnabled){
                nameButton.setImageResource(android.R.drawable.ic_media_play)
            } else {
                nameButton.setImageResource(android.R.drawable.ic_menu_edit)
            }
        }
    }

    private fun initDescriptionViews(view: View){
        descriptionEditText = view.findViewById(R.id.profile_description_editText)
        descriptionEditText.isEnabled = false
        descriptionButton = view.findViewById(R.id.profile_description_imageButton)
        descriptionButton.setOnClickListener{
            descriptionEditText.isEnabled = !descriptionEditText.isEnabled
            if(descriptionEditText.isEnabled){
                descriptionButton.setImageResource(android.R.drawable.ic_media_play)
            } else {
                descriptionButton.setImageResource(android.R.drawable.ic_menu_edit)
            }
        }
    }


}