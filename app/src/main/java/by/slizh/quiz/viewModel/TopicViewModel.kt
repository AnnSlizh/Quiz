package by.slizh.quiz.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.slizh.quiz.data.Topic
import by.slizh.quiz.repository.TopicRepository

class TopicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TopicRepository
    private val _allTopics = MutableLiveData<List<Topic>>()
    val allTopics: LiveData<List<Topic>> = _allTopics

    init {
        repository = TopicRepository().getInstance()
        repository.getTopics(_allTopics)
    }

}