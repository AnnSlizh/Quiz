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
import by.slizh.quiz.adapters.RatingAdapter
import by.slizh.quiz.viewModel.UserResultViewModel

class RatingFragment : Fragment() {

    private lateinit var adapter: RatingAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var userResultViewModel: UserResultViewModel

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

        userResultViewModel = ViewModelProvider(this)[UserResultViewModel::class.java]

        userResultViewModel.allUsersResults.observe(viewLifecycleOwner, Observer {
            adapter.setUserResultList(it)
        })
    }

    private fun setupRecyclerView() {
        adapter = RatingAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}



