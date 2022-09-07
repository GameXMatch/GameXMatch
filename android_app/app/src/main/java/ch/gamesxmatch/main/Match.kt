package ch.gamesxmatch.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.gamesxmatch.adaptator.MatchAdaptator
import ch.gamesxmatch.R
import ch.gamesxmatch.data.SharedData
import ch.gamesxmatch.data.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

/**
 * Activity displaying all matches a user has.
 *
 * This activity transitions to :
 *  - The chat between the user and the clicked match (Chat)
 *    NOTE : The transition was implemented in MatchAdaptator
 */
class Match : Fragment() {

    // Components
    lateinit var recycleView : RecyclerView

    // Data for components
    lateinit var parentView : View
    lateinit var parentContext: Context

    // Data
    val mainUser = SharedData.getInstance()

    var db = FirebaseDatabase.getInstance()
    private var fire = Firebase.firestore
    lateinit var dbListener : ValueEventListener
    lateinit var matchAdaptator: MatchAdaptator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(R.layout.fragment_match, container, false)
        parentContext = inflater.context

        initComponents()
        initListener()

        return parentView
    }

    /**
     * Initialises the components of the view
     */
    private fun initComponents() {
        recycleView = parentView.findViewById(R.id.match_recycleView)
        matchAdaptator = MatchAdaptator(ArrayList())
        recycleView.layoutManager = LinearLayoutManager(parentContext)
        recycleView.adapter = matchAdaptator
    }

    private fun initListener() {
        var dbRef = db.getReference("/members/")

        dbListener = object : ValueEventListener {
            var first = true
            override fun onDataChange(snapshot: DataSnapshot) {
                if(first) {
                    for (message in snapshot.children) {
                        checkMembers(message)
                    }
                    first = false
                }
                else {
                    checkMembers(snapshot.children.last())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        dbRef.addValueEventListener(dbListener)
    }

    fun checkMembers(key : DataSnapshot) {
        val uids = key.key?.split("_")
        if (uids != null) {
            if (uids.contains(mainUser.getMainUserUUID())) {
                fire.collection("Users").document(if (uids.first() == mainUser.getMainUserUUID()) uids.last() else uids.first())
                    .get()
                    .addOnSuccessListener { document ->
                        val user = User()
                        user.name = document.data?.get("name") as String
                        user.desc = document.data?.get("desc") as String

                        val tmp1 = ArrayList<String>()
                        for (doc in document.data?.get("likes") as ArrayList<DocumentReference>)
                        {
                            tmp1.add(doc.path)
                        }
                        user.likes = tmp1

                        val tmp2 = ArrayList<String>()
                        for (doc in document.data?.get("dislikes") as ArrayList<DocumentReference>)
                        {
                            tmp2.add(doc.path)
                        }
                        user.dislikes = tmp2

                        val tmp3 = ArrayList<String>()
                        for (doc in document.data?.get("games") as ArrayList<DocumentReference>)
                        {
                            tmp3.add(doc.path)
                        }
                        user.games = tmp3

                        user.imageURL = document.data?.get("imageURL") as String
                        user.uid = document.id
                        if (user != null) {
                            matchAdaptator.update(user)
                        }
                    }
            }
        }
    }
}