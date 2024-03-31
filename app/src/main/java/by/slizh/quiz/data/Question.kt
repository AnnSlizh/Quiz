package by.slizh.quiz.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question (
    val question: String,
    val answerList: List<String>,
    val correctAnswer: String
): Parcelable

{
    constructor() : this ("", emptyList(),"")
}