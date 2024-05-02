package by.slizh.quiz

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.slizh.quiz.data.Question
import by.slizh.quiz.databinding.ActivityQuizBinding
import by.slizh.quiz.databinding.ScoreDialogBinding
import by.slizh.quiz.viewModel.UserResultViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.random.Random

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    private lateinit var userResultViewModel: UserResultViewModel

    private lateinit var binding: ActivityQuizBinding

    private var currentQuestionIndex = 0;
    private var selectedAnswer = ""
    private var score = 0;

    companion object {
        var questionsList: List<Question> = listOf()
    }

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
            helpButton.setOnClickListener(this@QuizActivity)
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
            helpButton.visibility = View.VISIBLE
            answer3Btn.visibility = View.VISIBLE
            answer4Btn.visibility = View.VISIBLE
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
        } else if (clickedButton.id == R.id.helpButton) {
            selectedAnswer = ""

            binding.apply {
                var correctAnswerIndex = 0
                val buttons = arrayListOf(0, 1, 2, 3)

                helpButton.visibility = View.GONE

                for (i in 0..3) {
                    if (questionsList[currentQuestionIndex].answerList[i] == questionsList[currentQuestionIndex].correctAnswer) {
                        correctAnswerIndex = i
                        buttons.remove(i)
                        break
                    }
                }

                val randomIndex = (0 until buttons.size).random()
                val randomValue = buttons[randomIndex]

                answer1Btn.text = questionsList[currentQuestionIndex].answerList[randomValue]
                answer2Btn.text = questionsList[currentQuestionIndex].correctAnswer
                answer3Btn.visibility = View.GONE
                answer4Btn.visibility = View.GONE
            }


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
