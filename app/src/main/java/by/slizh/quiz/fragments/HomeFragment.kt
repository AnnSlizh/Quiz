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
import by.slizh.quiz.data.Topic
import by.slizh.quiz.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database

//class HomeFragment : Fragment() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: HomeAdapter
//
//    private lateinit var topicsList: MutableList<Topic>
//
//
//
//    //   private lateinit var topicViewModel: TopicViewModel
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val layoutManager = LinearLayoutManager(context)
//        recyclerView = view.findViewById(R.id.recycleViewTopics)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.setHasFixedSize(true)
//
//
//
//
//        topicsList = mutableListOf()
//
//        adapter = context?.let { HomeAdapter(it,topicsList) }!!
//        recyclerView.adapter = adapter
//
////        topicViewModel = ViewModelProvider(this).get(TopicViewModel::class.java)
////
////        topicViewModel.allTopics.observe(viewLifecycleOwner, Observer { topics ->
////            adapter.setData(topics)
////            Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + topics.toString())
////        })
//
//
//        getTopicsFromDatabase()
//
//        adapter.setOnStartQuizListener {
////
////           val intent = Intent(context, QuizActivity::class.java)
////            intent.putExtra("questionList", it)
////            startActivity(intent)
//
//
//
//
////            val action = HomeFragmentDirections.actionHomeFragmentToQuizFragment(it)
////            Navigation.findNavController(view).navigate(action)
//        }
//
//    }
//
//    private fun getTopics() {
//
//    }
//
//    private fun getTopicsFromDatabase() {
//
//        Firebase.database.reference.child("topics").get()
//            .addOnSuccessListener { dataSnapshot: DataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    for (snapshot in dataSnapshot.children) {
//                        val topic = snapshot.getValue(Topic::class.java)
//                        if (topic != null) {
//                            topicsList.add(topic)
//                            adapter.setData(topicsList)
//                            Log.d(
//                                ContentValues.TAG,
//                                "firebaseAuthWithGoogle:" + topicsList.toString()
//                            )
//
//                        }
//                    }
//                }
//
//
//
//
//
//
//
//            }
//    }
//
//}

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var quizModelList : MutableList<Topic>
    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycleViewTopics)

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView(){
        adapter = HomeAdapter(quizModelList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){

        Firebase.database.reference.child("topics")
            .get()
            .addOnSuccessListener { dataSnapshot->
                if(dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(Topic::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                            Log.i("Score of quiz", quizModel.toString())
                        }
                    }
                }
                setupRecyclerView()
            }

    }
}