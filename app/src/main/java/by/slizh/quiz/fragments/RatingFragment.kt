package by.slizh.quiz.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.slizh.quiz.R
import by.slizh.quiz.adapters.HomeAdapter
import by.slizh.quiz.adapters.RatingAdapter
import by.slizh.quiz.data.Topic
import by.slizh.quiz.data.UserResult
import by.slizh.quiz.databinding.FragmentHomeBinding
import by.slizh.quiz.viewModel.TopicViewModel
import by.slizh.quiz.viewModel.UserResultViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RatingFragment: Fragment() {

    private lateinit var userResultList : MutableList<UserResult>
    private lateinit var adapter: RatingAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel : UserResultViewModel

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

        setupRecyclerView()
        //userResultList = mutableListOf()
        viewModel = ViewModelProvider(this)[UserResultViewModel::class.java]

        viewModel.allUsersResults.observe(viewLifecycleOwner, Observer {

            adapter.updateUserResultList(it)

        })

      //  getDataFromFirebase()
    }

    private fun setupRecyclerView(){
       // adapter = RatingAdapter(userResultList)
        adapter = RatingAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter


    }

    private fun getDataFromFirebase(){

//        Firebase.database.reference.child("quizResults")
//            .get()
//            .addOnSuccessListener { dataSnapshot->
//                if(dataSnapshot.exists()){
//                    for (snapshot in dataSnapshot.children){
//                        val userResult = snapshot.getValue(UserResult::class.java)
//                        if (userResult != null) {
//                            userResultList.add(userResult)
//                            Log.i("Score of quiz", userResult.toString())
//                        }
//                    }
//                }
//                setupRecyclerView()
//            }
//


   }
}


