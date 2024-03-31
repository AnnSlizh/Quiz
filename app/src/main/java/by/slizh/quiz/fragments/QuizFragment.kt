package by.slizh.quiz.fragments

import android.app.AlertDialog
import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import by.slizh.quiz.R
import by.slizh.quiz.data.Question
import by.slizh.quiz.data.Topic
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database

class QuizFragment : Fragment(), View.OnClickListener {

    private val quizArgs: QuizFragmentArgs by navArgs<QuizFragmentArgs>()
    private lateinit var currentTopic: Topic

    private var currentQuestionIndex = 0
    private var selectedAnswer = "";
    private var score = 0

    private var questionList: List<Question> = listOf()

    private lateinit var answer1Button: Button
    private lateinit var answer2Button: Button
    private lateinit var answer3Button: Button
    private lateinit var answer4Button: Button
    private lateinit var questionIndicator: TextView
    private lateinit var questionProcessIndicator: ProgressBar
    private lateinit var question: TextView
    private lateinit var nextButton: Button

    private lateinit var scoreProgressIndicator: ProgressBar
    private lateinit var scoreProgressText: TextView
    private lateinit var scoreTitle: TextView
    private lateinit var scoreSubtitle: TextView
    private lateinit var finishButton: Button

    private lateinit var topicsList: MutableList<Topic>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answer1Button = view.findViewById(R.id.answer1_btn)
        answer2Button = view.findViewById(R.id.answer2_btn)
        answer3Button = view.findViewById(R.id.answer3_btn)
        answer4Button = view.findViewById(R.id.answer4_btn)

        question = view.findViewById(R.id.question_textview)

        currentTopic = quizArgs.topic

        Log.d(ContentValues.TAG, "TopicArgs" + currentTopic.questionList.toString())



        loadQuestions()
        onClick(view)

        topicsList = mutableListOf()
        //  getTopicsFromDatabase()


    }

//    private fun getTopicsFromDatabase() {
//
//        Firebase.database.reference.child("topics").get()
//            .addOnSuccessListener { dataSnapshot: DataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    for (snapshot in dataSnapshot.children) {
//                        val topic = snapshot.getValue(Topic::class.java)
//                        if (topic != null) {
//                            topicsList.add(topic)
//                            Log.d(
//                                ContentValues.TAG,
//                                "firebaseAuthWithGoogle:" + topicsList.toString()
//                            )
//
//                        }
//                    }
//                }
//            }
//    }

    private fun loadQuestions() {
        if (currentQuestionIndex == currentTopic.questionList.size) {
              finishQuiz()
            return
        }

//        questionIndicator.text = "Question ${currentQuestionIndex + 1}/ ${currentTopic.questionList.size} "
//        questionProcessIndicator.progress =
//            (currentQuestionIndex.toFloat() / currentTopic.questionList.size.toFloat() * 100).toInt()

        question.text = currentTopic.questionList[currentQuestionIndex].question
        answer1Button.text = currentTopic.questionList[currentQuestionIndex].answerList[0]
        answer2Button.text = currentTopic.questionList[currentQuestionIndex].answerList[1]
        answer3Button.text = currentTopic.questionList[currentQuestionIndex].answerList[2]
        answer4Button.text = currentTopic.questionList[currentQuestionIndex].answerList[3]


    }

    override  fun onClick(view: View) {
        answer1Button.setBackgroundColor(Color.GRAY)
        answer2Button.setBackgroundColor(Color.GRAY)
        answer3Button.setBackgroundColor(Color.GRAY)
        answer4Button.setBackgroundColor(Color.GRAY)

        val clickedButton = view as Button

        nextButton = view.findViewById(R.id.nextButton)
        if (clickedButton.id == nextButton.id) {

            if (selectedAnswer.isEmpty()) {
                Toast.makeText(context, "Please select answer to continue", Toast.LENGTH_SHORT)
                    .show()
                return;
            }
            if (selectedAnswer == currentTopic.questionList[currentQuestionIndex].correctAnswer) {
                score++
                Log.i("Score of quiz", score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        } else {
            //options button is clicked
            selectedAnswer = clickedButton.text.toString()
            clickedButton.setBackgroundColor(resources.getColor(R.color.pink))
        }
    }

    private fun finishQuiz() {
        val totalQuestions = currentTopic.questionList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()


        val scoreDialog = LayoutInflater.from(context).inflate(R.layout.score_dialog, null, false)

        scoreProgressIndicator = scoreDialog.findViewById(R.id.score_progress_indicator)
        scoreProgressIndicator.progress = percentage

        scoreProgressText = scoreDialog.findViewById(R.id.score_progress_text)
        scoreProgressText.text = "$percentage %"

        scoreTitle = scoreDialog.findViewById(R.id.score_title)
        scoreSubtitle = scoreDialog.findViewById(R.id.score_subtitle)

        if (percentage > 60) {
            scoreTitle.text = "Congrats! You have passed"
            scoreTitle.setTextColor(Color.BLUE)
        } else {
            scoreTitle.text = "Oops! You have failed"
            scoreTitle.setTextColor(Color.RED)
        }
        scoreSubtitle.text = "$score out of $totalQuestions are correct"
        finishButton = scoreDialog.findViewById(R.id.finish_btn)
        finishButton.setOnClickListener {
        }

        AlertDialog.Builder(context)
            .setView(scoreDialog)
            .setCancelable(false)
            .show()

    }


}
