package by.slizh.quiz.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import by.slizh.quiz.data.Topic
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TopicRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("topics")

    @Volatile
    private var INSTANCE: TopicRepository? = null

    fun getInstance(): TopicRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = TopicRepository()
            INSTANCE = instance
            instance
        }
    }

    fun getTopics(topicList: MutableLiveData<List<Topic>>) {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {
                    val _topicList: List<Topic> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Topic::class.java)!!
                    }
                    topicList.postValue(_topicList)

                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Error getting data")
            }
        })
    }
}