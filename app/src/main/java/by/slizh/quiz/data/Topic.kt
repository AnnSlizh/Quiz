package by.slizh.quiz.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Topic (
    val id: Int,
    val title: String,
    val subtitle: String,
    val questionList: List<Question>
):Parcelable
{
    constructor() : this(0,"","", emptyList())
}

