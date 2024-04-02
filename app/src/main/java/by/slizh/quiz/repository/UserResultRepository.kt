package by.slizh.quiz.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import by.slizh.quiz.data.UserResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserResultRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("quizResults")

    @Volatile
    private var INSTANCE: UserResultRepository? = null

    fun getInstance(): UserResultRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = UserResultRepository()
            INSTANCE = instance
            instance
        }
    }

    fun getUsersResults(userResultList: MutableLiveData<List<UserResult>>) {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {
                    val _userResultList: List<UserResult> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(UserResult::class.java)!!
                    }
                    userResultList.postValue(_userResultList)

                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Error getting data")
            }
        })
    }

    fun addUserResult(userId: String, userResult: HashMap<String, Any?>) {

        databaseReference.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (!snapshot.exists()) {

                    databaseReference.child(userId)
                        .setValue(userResult)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Error getting data")
            }
        })
    }

    fun getUserResultById(userId: String, userResultData: (UserResult?) -> Unit) {
        databaseReference.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val userResult: UserResult? = snapshot.getValue(UserResult::class.java)
                userResultData(userResult)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Error getting data")
            }
        })
    }

    fun updateUserResult(userId: String, score: Int) {

        databaseReference.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    val userResult = snapshot.getValue(UserResult::class.java)

                    if (userResult != null) {
                        if (userResult.bestResult < score) {
                            databaseReference.child(userId)
                                .child("bestResult").setValue(score)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Error getting data")
            }
        })
    }
}
