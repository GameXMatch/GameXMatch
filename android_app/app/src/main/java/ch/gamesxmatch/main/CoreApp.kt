package ch.gamesxmatch.main

import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ch.gamesxmatch.R

class CoreApp : AppCompatActivity() {
    lateinit var btnMatch : Button
    lateinit var btnSwipe : Button
    lateinit var btnProfile : Button
    val fragmentID = R.id.fragmentContainerView

    val matchFragment = Match()
    val swipeFragment = Swipe()
    val profileFragment = Profile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)



        //switchFragments(fragmentID, profileFragment)

        btnMatch = findViewById(R.id.core_match_button)
        btnSwipe = findViewById(R.id.core_swipe_button)
        btnProfile = findViewById(R.id.core_profile_button)

        initButtonListeners()

        /*
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, profileFragment)
            commit()
        }
        */
    }

    fun initButtonListeners(){
        btnMatch.setOnClickListener{
            switchFragments(fragmentID, matchFragment)
        }

        btnSwipe.setOnClickListener{
            switchFragments(fragmentID, swipeFragment)
        }

        btnProfile.setOnClickListener{
            switchFragments(fragmentID, profileFragment)
        }
    }

    fun switchFragments(id : Int, profile : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(id, profile)
            commit()
        }
    }
}