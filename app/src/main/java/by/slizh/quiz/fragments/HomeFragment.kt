package by.slizh.quiz.fragments


import android.os.Bundle
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
import by.slizh.quiz.viewModel.TopicViewModel


class HomeFragment : Fragment() {

    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var topicViewModel : TopicViewModel

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
        setupRecyclerView()

        topicViewModel = ViewModelProvider(this)[TopicViewModel::class.java]

        topicViewModel.allTopics.observe(viewLifecycleOwner, Observer {
            adapter.setTopicsData(it)

        })
    }

    private fun setupRecyclerView(){
        adapter = HomeAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}