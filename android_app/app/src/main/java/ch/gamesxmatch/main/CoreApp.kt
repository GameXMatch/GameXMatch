package ch.gamesxmatch.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ch.gamesxmatch.R

/**
 * Main Activity of the app. Allows switching smoothly between the following three fragments :
 *  - User's profile (Profile)
 *  - Swipe cards, allowing to match (Swipe)
 *  - Match list (Match)
 *
 * For the possible activity transitions, check every fragment individually.
 */
class CoreApp : AppCompatActivity() {

    // Components
    lateinit var btnMatch : Button
    lateinit var btnSwipe : Button
    lateinit var btnProfile : Button

    // Fragments
    val fragmentID = R.id.fragmentContainerView
    val matchFragment = Match()
    val swipeFragment = Swipe()
    val profileFragment = Profile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)

        btnMatch = findViewById(R.id.core_match_button)
        btnSwipe = findViewById(R.id.core_swipe_button)
        btnProfile = findViewById(R.id.core_profile_button)

        switchFragmentAndDisableButtons(btnProfile, profileFragment)

        initButtonListeners()
    }


    /**
     * Initialise the three buttons allowing to switch between fragments
     */
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

    /**
     * Switches the fragment
     *
     * @param button Clicked button
     * @param fragment fragment to switch to
     */
    fun switchFragmentAndDisableButtons(button: Button, fragment: Fragment) {
        switchFragments(fragment)
        disableClickedAndEnableRest(button)
    }

    /**
     * Replaces the current fragment in the fragment container, with a given new one
     * @param fragment Fragment to switch to
     */
    fun switchFragments(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(fragmentID, fragment)
            commit()
        }
    }

    /**
     * Re-enables all the buttons and disabled the given button
     * @param button Button to disable
     */
    fun disableClickedAndEnableRest(button : Button){
        btnProfile.isEnabled = true
        btnMatch.isEnabled = true
        btnSwipe.isEnabled = true

        button.isEnabled = false
    }
}