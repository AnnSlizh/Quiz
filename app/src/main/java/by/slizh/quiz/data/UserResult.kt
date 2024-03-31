package by.slizh.quiz.data

data class UserResult (
    val userId: Int,
    val userName: String,
    val bestResult: Int
)
{
    constructor() : this(0,"",0)
}
