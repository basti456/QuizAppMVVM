package com.example.quizappmvvm.presentation.quiz

sealed class QuizScreenEvents {
    data class GetQuizzes(
        val numOfQuizzes: Int,
        val category: Int,
        val difficulty: String,
        val type: String
    ) : QuizScreenEvents()

    data class SetOptionSelected(val quizStateIndex: Int, val selectedOption: Int) :
        QuizScreenEvents()
}
