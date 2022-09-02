package ch.gamesxmatch.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import ch.gamesxmatch.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.*
import java.util.*
import kotlin.collections.ArrayList


class Swipe : Fragment(), CardStackListener {

    // TODO : Create data type for the match
    // TODO : Find a way to display the data
    // TODO : Implement the swipe logic and refresh of the card
    // Maybe find something better than a Cardview?

    lateinit var btnSwipeLeft : Button
    lateinit var btnSwipeRight : Button
    lateinit var stack_view: CardStackView

    lateinit var db: FirebaseFirestore

    var profiles = ArrayList<Profile>(arrayListOf(Profile("DSAJHKDHSAKHD234423","test1","desc1", arrayListOf("asdasd", "vcbcvbcvb")),
        Profile("HJJKHGUJ234KH","test2","desc2", arrayListOf("asdgfdgd", "dfg")),
        Profile("SFDHJFSDJHHB1231","test3","desc3", arrayListOf("sdfghg", "vcvxvcbcvdgfdfabcvb"))))

    var adapter = SwipeAdapter(profiles)
    lateinit var layoutManager: CardStackLayoutManager

    data class Profile(
        val uuid: String,
        val name: String,
        val description: String,
        val games_images: ArrayList<String>
    )

    fun getMatches(uid: String) {
        val games = db.collection("Users")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
                val games = result.documents[0].data?.get("games") as ArrayList<DocumentReference>

                db.collection("Users")
                    .whereArrayContainsAny("games", games)
                    .whereNotEqualTo("uid", uid)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.d("FIRETEST", document.data.toString())
                        }
                    }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe, container, false)
        btnSwipeLeft = view.findViewById(R.id.swipe_left_button)
        btnSwipeRight = view.findViewById(R.id.swipe_right_button)
        stack_view = view.findViewById(R.id.stack_view_id)

        layoutManager = CardStackLayoutManager(this.context, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        db = Firebase.firestore
        getMatches("Heraciel")

        stack_view.layoutManager = layoutManager
        stack_view.adapter = adapter
        stack_view.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        btnSwipeLeft.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(200)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            stack_view.swipe()
        }

        btnSwipeRight.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(200)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            stack_view.swipe()
        }

        return view
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        Toast.makeText(this.getContext(), if (direction == Direction.Left) "Left" else "Right"  ,Toast.LENGTH_LONG).show();
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }
}