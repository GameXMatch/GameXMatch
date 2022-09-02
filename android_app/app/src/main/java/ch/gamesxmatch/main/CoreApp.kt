package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ch.gamesxmatch.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CoreApp : AppCompatActivity() {
    lateinit var btnMatch : Button
    lateinit var btnSwipe : Button
    lateinit var btnProfile : Button
    val fragmentID = R.id.fragmentContainerView
    val images = Images.getInstance()

    val matchFragment = Match()
    val swipeFragment = Swipe()
    val profileFragment = Profile()

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)

        btnMatch = findViewById(R.id.core_match_button)
        btnSwipe = findViewById(R.id.core_swipe_button)
        btnProfile = findViewById(R.id.core_profile_button)

        switchFragmentAndDisableButtons(btnProfile, profileFragment)
        db = Firebase.firestore
        initButtonListeners()
        //initGamesFolder()
    }


    fun initButtonListeners(){
        btnMatch.setOnClickListener{
            switchFragmentAndDisableButtons(btnMatch, matchFragment)
        }

        btnSwipe.setOnClickListener{
            switchFragmentAndDisableButtons(btnSwipe, swipeFragment)
        }

        btnProfile.setOnClickListener{
            switchFragmentAndDisableButtons(btnProfile, profileFragment)
        }
    }

    fun switchFragmentAndDisableButtons(button: Button, fragment: Fragment){
        switchFragments(fragmentID, fragment)
        disableClickedAndEnableRest(button)
    }

    fun switchFragments(id : Int, profile : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(id, profile)
            commit()
        }
    }

    fun disableClickedAndEnableRest(button : Button){
        btnProfile.isEnabled = true
        btnMatch.isEnabled = true
        btnSwipe.isEnabled = true

        button.isEnabled = false
    }
}