package ch.gamesxmatch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.StackView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.R
import com.yuyakaido.android.cardstackview.*

class Swipe : Fragment(), CardStackListener {

    // TODO : Create data type for the match
    // TODO : Find a way to display the data
    // TODO : Implement the swipe logic and refresh of the card
    // Maybe find something better than a Cardview?

    lateinit var btnSwipeLeft : Button
    lateinit var btnSwipeRight : Button
    lateinit var stack_view: CardStackView

    var profiles = ArrayList<Profile>(arrayListOf(Profile(123,"test1","desc1", arrayListOf("asdasd", "vcbcvbcvb")),
        Profile(234,"test2","desc2", arrayListOf("asdgfdgd", "dfg")),
        Profile(456,"test3","desc3", arrayListOf("sdfghg", "vcvxvcbcvdgfdfabcvb"))))

    var adapter = SwipeAdapter(profiles)
    lateinit var layoutManager: CardStackLayoutManager

    data class Profile(
        val uuid: Int,
        val name: String,
        val description: String,
        val games_images: ArrayList<String>
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe, container, false)
        btnSwipeLeft = view.findViewById(R.id.swipe_left_button)
        btnSwipeRight = view.findViewById(R.id.swipe_right_button)
        stack_view = view.findViewById(R.id.stack_view_id)

        layoutManager = CardStackLayoutManager(context).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        stack_view.layoutManager = layoutManager
        stack_view.adapter = adapter
        stack_view.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        return view
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun onCardSwiped(direction: Direction?) {
        TODO("Not yet implemented")
    }

    override fun onCardRewound() {
        TODO("Not yet implemented")
    }

    override fun onCardCanceled() {
        TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }
}