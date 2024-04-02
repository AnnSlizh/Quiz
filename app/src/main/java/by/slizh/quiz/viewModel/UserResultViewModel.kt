package by.slizh.quiz.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.slizh.quiz.data.UserResult
import by.slizh.quiz.repository.UserResultRepository
import kotlinx.coroutines.launch

class UserResultViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserResultRepository = UserResultRepository().getInstance()

    private val _allUsersResults = MutableLiveData<List<UserResult>>()
    val allUsersResults: LiveData<List<UserResult>> = _allUsersResults

    private val _userResult = MutableLiveData<UserResult?>()
    val userResult: LiveData<UserResult?> = _userResult

    init {
        repository.getUsersResults(_allUsersResults)
    }

    fun getUserResultById(userId: String) {
        repository.getUserResultById(userId) { userResult ->
            _userResult.value = userResult
        }
    }
    fun addUserResult(userId: String, userResult: HashMap<String, Any?>) {
        viewModelScope.launch {
            repository.addUserResult(userId, userResult)
        }
    }

    fun updateUserResult(userId: String, score: Int) {
        viewModelScope.launch {
            repository.updateUserResult(userId, score)
        }
    }
}