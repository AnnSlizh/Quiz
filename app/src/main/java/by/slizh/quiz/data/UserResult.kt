package by.slizh.quiz.data

data class UserResult (
    val userId: String,
    val userName: String,
    val bestResult: Int
)
{
    constructor() : this("","",0)
}
