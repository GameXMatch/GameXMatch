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
import ch.gamesxmatch.adaptator.SwipeAdapter
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.*
import kotlin.collections.ArrayList


class Swipe : Fragment(), CardStackListener {

    // TODO : Implement refresh of the cards
    // TODO : Use real uid of user

    lateinit var btnSwipeLeft : Button
    lateinit var btnSwipeRight : Button
    lateinit var stack_view: CardStackView

    lateinit var db: FirebaseFirestore

    lateinit var adapter : SwipeAdapter
    lateinit var layoutManager: CardStackLayoutManager

    fun getMatches(uid: String) {
        val tmp = ArrayList<User>()

        db.collection("Users")
            .document(uid)
            .get()
            .addOnSuccessListener { result ->
                val games = result.data?.get("games") as ArrayList<DocumentReference>

                db.collection("Users")
                    .whereArrayContainsAny("games", games)
                    .whereNotEqualTo(FieldPath.documentId(), uid)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            println(document.data)
                            tmp.add(document.toObject<User>())

                        }
                        adapter = SwipeAdapter(tmp)

                        stack_view.layoutManager = layoutManager
                        stack_view.adapter = adapter
                        stack_view.itemAnimator.apply {
                            if (this is DefaultItemAnimator) {
                                supportsChangeAnimations = false
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("Query2", "get failed with ", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.d("Query1", "get failed with ", exception)
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
        getMatches("I4RNOiGQmxQ5BCyQvBtg")

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
        Toast.makeText(this.getContext(), if (direction == Direction.Left) "Left" else "Right"  ,Toast.LENGTH_SHORT).show()

        val swipedUser = adapter.user[layoutManager.topPosition - 1]

        //update likes/dislikes
        val uRef = db.collection("Users").document("I4RNOiGQmxQ5BCyQvBtg")
        uRef.update(if (direction == Direction.Left) "dislikes" else "likes", FieldValue.arrayUnion("/Users/" + swipedUser.uid))
            .addOnSuccessListener { Log.d("SWIPE", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("SWIPE", "Error updating document", e) }

        //TODO : create conversation
        if (SharedData.user.likes.contains(("/Users/" + swipedUser.uid) as DocumentReference) && swipedUser.likes.contains(("/Users/" + SharedData.user.uid) as DocumentReference)) {
            println("Match")
        }

        if (layoutManager.topPosition == adapter.itemCount) {
            Toast.makeText(this.getContext(), "No more match",Toast.LENGTH_LONG).show()
        }
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