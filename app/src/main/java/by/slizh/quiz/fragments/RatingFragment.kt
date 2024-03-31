package by.slizh.quiz.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.slizh.quiz.R
import by.slizh.quiz.adapters.HomeAdapter
import by.slizh.quiz.adapters.RatingAdapter
import by.slizh.quiz.data.Topic
import by.slizh.quiz.data.UserResult
import by.slizh.quiz.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RatingFragment: Fragment() {

    private lateinit var userResultList : MutableList<UserResult>
    private lateinit var adapter: RatingAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.ratingRecyclerView)

        userResultList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView(){
        adapter = RatingAdapter(userResultList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){

        Firebase.database.reference.child("quizResults")
            .get()
            .addOnSuccessListener { dataSnapshot->
                if(dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val userResult = snapshot.getValue(UserResult::class.java)
                        if (userResult != null) {
                            userResultList.add(userResult)
                            Log.i("Score of quiz", userResult.toString())
                        }
                    }
                }
                setupRecyclerView()
            }

    }
}


