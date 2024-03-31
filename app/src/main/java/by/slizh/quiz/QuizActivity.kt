package by.slizh.quiz

import android.app.AlertDialog
import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.slizh.quiz.data.Question
import by.slizh.quiz.data.Topic
import by.slizh.quiz.data.UserResult
import by.slizh.quiz.databinding.ActivityQuizBinding
import by.slizh.quiz.databinding.ScoreDialogBinding
import by.slizh.quiz.fragments.QuizFragmentArgs
import by.slizh.quiz.viewModel.TopicViewModel
import by.slizh.quiz.viewModel.UserResultViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class QuizActivity : AppCompatActivity(), View.OnClickListener {
//
//    // private val quizArgs: QuizFragmentArgs by navArgs<QuizFragmentArgs>()
//    private lateinit var currentTopic: Topic
//
//    private var currentQuestionIndex = 0
//    private var selectedAnswer = "";
//    private var score = 0
//
//    private var questionList: List<Question> = listOf()
//    private lateinit var topicsList: List<Topic>
//
//
//
//    private lateinit var answer1Button: Button
//    private lateinit var answer2Button: Button
//    private lateinit var answer3Button: Button
//    private lateinit var answer4Button: Button
//    private lateinit var questionIndicator: TextView
//    private lateinit var questionProcessIndicator: ProgressBar
//    private lateinit var question: TextView
//    private lateinit var nextButton: Button
//
//    private lateinit var scoreProgressIndicator: ProgressBar
//    private lateinit var scoreProgressText: TextView
//    private lateinit var scoreTitle: TextView
//    private lateinit var scoreSubtitle: TextView
//    private lateinit var finishButton: Button
//
//   // private var topicsList: List<Topic> = listOf()
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_quiz)
//
//        answer1Button = findViewById(R.id.answer1_btn)
//        answer2Button = findViewById(R.id.answer2_btn)
//        answer3Button = findViewById(R.id.answer3_btn)
//        answer4Button = findViewById(R.id.answer4_btn)
//        nextButton = findViewById(R.id.nextButton)
//
//        answer1Button.setOnClickListener(this@QuizActivity)
//        answer2Button.setOnClickListener(this@QuizActivity)
//        answer3Button.setOnClickListener(this@QuizActivity)
//        answer4Button.setOnClickListener(this@QuizActivity)
//        nextButton.setOnClickListener(this@QuizActivity)
//
//        question = findViewById(R.id.question_textview)
//
//        // currentTopic = quizArgs.topic
//
////        Log.d(ContentValues.TAG, "TopicArgs" + currentTopic.questionList.toString())
//        val bundle = intent.extras
//        bundle?.let {
//            val f = it.getString("Title")
//            question.text = it.getString("Description")
//            // val key = it.getString("Key") ?: ""
//            if (f != null) {
//                getTopicsFromDatabase(f)
//            }
//            Log.d(
//                ContentValues.TAG,
//                "firebaseAuthWithGoogle:$f"
//            )
//        }
//        loadQuestions()
//
//
//       //    topicsList = mutableListOf()
//        //  getTopicsFromDatabase()
//
//
//
//    }
//
//    private fun getTopicsFromDatabase(key: String) {
//
//        Firebase.database.reference.child("topics").child(key).get()
//            .addOnSuccessListener { dataSnapshot: DataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    for (snapshot in dataSnapshot.children) {
//                        val topic = snapshot.getValue(Topic::class.java)
//                        if (topic != null) {
//                            currentTopic = topic
//                          //  topicsList.add(topic)
//
//                            Log.d(
//                                ContentValues.TAG,
//                                "firebaseAuthWithGoogle:${topic.questionList}"
//                            )
//
//                        }
//                    }
//                }
//            }
//    }
//
//    private fun loadQuestions() {
//        if (currentQuestionIndex == questionList.size) {
//            //   finishQuiz()
//            return
//        }
//
//
//
//      //  val f = intent.extras?.getParcelable<Topic>("topic")
//
////        questionIndicator.text = "Question ${currentQuestionIndex + 1}/ ${currentTopic.questionList.size} "
////        questionProcessIndicator.progress =
////            (currentQuestionIndex.toFloat() / currentTopic.questionList.size.toFloat() * 100).toInt()
//
//        question.text =currentTopic.questionList[currentQuestionIndex].question
//    //    answer1Button.text = topicsList[currentQuestionIndex].answerList[1]
//        answer2Button.text = questionList[currentQuestionIndex].answerList[2]
//        answer3Button.text = questionList[currentQuestionIndex].answerList[3]
//        answer4Button.text = questionList[currentQuestionIndex].answerList[4]
//        Log.d(
//            ContentValues.TAG,
//            "firebaseAuthWithGoogle:" + questionList[currentQuestionIndex].question
//        )
//
//    }
//
//    override fun onClick(view: View?) {
//        answer1Button.setBackgroundColor(Color.GRAY)
//        answer2Button.setBackgroundColor(Color.GRAY)
//        answer3Button.setBackgroundColor(Color.GRAY)
//        answer4Button.setBackgroundColor(Color.GRAY)
//
//        val clickedButton = view as Button
//
//
//        if (clickedButton.id == nextButton.id) {
//
//            if (selectedAnswer.isEmpty()) {
//                Toast.makeText(
//                    applicationContext,
//                    "Please select answer to continue",
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//                return;
//            }
//            if (selectedAnswer == questionList[currentQuestionIndex].correctAnswer) {
//                score++
//                Log.i("Score of quiz", score.toString())
//            }
//            currentQuestionIndex++
//            loadQuestions()
//        } else {
//            //options button is clicked
//            selectedAnswer = clickedButton.text.toString()
//            clickedButton.setBackgroundColor(resources.getColor(R.color.pink))
//        }
//    }

//    private fun finishQuiz() {
//        val totalQuestions = questionList.size
//        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()
//
//
//        val scoreDialog = LayoutInflater.from(context).inflate(R.layout.score_dialog, null, false)
//
//        scoreProgressIndicator = scoreDialog.findViewById(R.id.score_progress_indicator)
//        scoreProgressIndicator.progress = percentage
//
//        scoreProgressText = scoreDialog.findViewById(R.id.score_progress_text)
//        scoreProgressText.text = "$percentage %"
//
//        scoreTitle = scoreDialog.findViewById(R.id.score_title)
//        scoreSubtitle = scoreDialog.findViewById(R.id.score_subtitle)
//
//        if (percentage > 60) {
//            scoreTitle.text = "Congrats! You have passed"
//            scoreTitle.setTextColor(Color.BLUE)
//        } else {
//            scoreTitle.text = "Oops! You have failed"
//            scoreTitle.setTextColor(Color.RED)
//        }
//        scoreSubtitle.text = "$score out of $totalQuestions are correct"
//        finishButton = scoreDialog.findViewById(R.id.finish_btn)
//        finishButton.setOnClickListener {
//        }
//
//        AlertDialog.Builder(context)
//            .setView(scoreDialog)
//            .setCancelable(false)
//            .show()
//
//    }
//}


class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    private lateinit var userResultViewModel: UserResultViewModel

    companion object {
        var questionsList: List<Question> = listOf()
    }

    lateinit var binding: ActivityQuizBinding

    private var currentQuestionIndex = 0;
    private var selectedAnswer = ""
    private var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            answer1Btn.setOnClickListener(this@QuizActivity)
            answer2Btn.setOnClickListener(this@QuizActivity)
            answer3Btn.setOnClickListener(this@QuizActivity)
            answer4Btn.setOnClickListener(this@QuizActivity)
            nextButton.setOnClickListener(this@QuizActivity)
        }

        loadQuestions()


        auth = Firebase.auth

    }

    private fun saveUserResult() {
        val currentUser = auth.currentUser

        userResultViewModel = ViewModelProvider(this)[UserResultViewModel::class.java]

        if (currentUser != null) {


            userResultViewModel.getUserResultById(currentUser.uid)

            userResultViewModel.userResult.observe(this, Observer { userResult ->

                if (userResult != null) {
                    if (userResult.bestResult < score) {
                        userResultViewModel.updateUserResult(currentUser.uid, score)
                    }

                } else {
                    val map = HashMap<String, Any?>()
                    map["id"] = currentUser.uid
                    map["userName"] = currentUser.displayName
                    map["bestResult"] = score
                    Log.i("firebase", "Got value ${currentUser.displayName}")

                    userResultViewModel.addUserResult(currentUser.uid, map)
                }

            })


//            val usersReference =
//                Firebase.database.reference.child("quizResults").child(currentUser.uid)
//            usersReference.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    if (!snapshot.exists()) {
//                        val map = HashMap<String, Any?>()
//                        map["id"] = currentUser.uid
//                        map["userName"] = currentUser.displayName
//                        map["bestResult"] = score
//                        Log.i("firebase", "Got value ${currentUser.displayName}")
//
//                        Firebase.database.reference.child("quizResults").child(currentUser.uid)
//                            .setValue(map)
//                    } else {
//                        val userResult = snapshot.getValue(UserResult::class.java)
//                        Log.i("firebase", "Result for db $userResult")
//                        if (userResult != null) {
//                            if (userResult.bestResult < score) {
//                                Firebase.database.reference.child("quizResults").child(currentUser.uid)
//                                    .child("bestResult").setValue(score)
//                            }
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.e("firebase", "Error getting data")
//                }
//            })
        }
    }

    private fun loadQuestions() {
        selectedAnswer = ""
        if (currentQuestionIndex == questionsList.size) {
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextview.text =
                "Question ${currentQuestionIndex + 1}/ ${questionsList.size} "
            questionProgressIndicator.progress =
                (currentQuestionIndex.toFloat() / questionsList.size.toFloat() * 100).toInt()
            questionTextview.text = questionsList[currentQuestionIndex].question
            answer1Btn.text = questionsList[currentQuestionIndex].answerList[0]
            answer2Btn.text = questionsList[currentQuestionIndex].answerList[1]
            answer3Btn.text = questionsList[currentQuestionIndex].answerList[2]
            answer4Btn.text = questionsList[currentQuestionIndex].answerList[3]
        }
    }

    override fun onClick(view: View?) {

        binding.apply {
            answer1Btn.setBackgroundColor(getColor(R.color.light_gray))
            answer2Btn.setBackgroundColor(getColor(R.color.light_gray))
            answer3Btn.setBackgroundColor(getColor(R.color.light_gray))
            answer4Btn.setBackgroundColor(getColor(R.color.light_gray))
        }

        val clickedButton = view as Button
        if (clickedButton.id == R.id.nextButton) {
            //next button is clicked
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Please select answer to continue",
                    Toast.LENGTH_SHORT
                ).show()
                return;
            }
            if (selectedAnswer == questionsList[currentQuestionIndex].correctAnswer) {
                score++
                Log.i("Score of quiz", score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        } else {
            //options button is clicked
            selectedAnswer = clickedButton.text.toString()
            clickedButton.setBackgroundColor(getColor(R.color.light_green))
        }
    }

    private fun finishQuiz() {
        val totalQuestions = questionsList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if (percentage > 60) {
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener {
                saveUserResult()
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }
}
