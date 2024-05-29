package com.example.quizappmvvm.presentation.home

sealed class HomeScreenEvents {

    data class SetNumberOfQuizzes(val numberOfQuizzes: Int) : HomeScreenEvents()
    data class SetQuizCategory(val quizCategory: String) : HomeScreenEvents()
    data class SetQuizDifficulty(val quizDifficulty: String) : HomeScreenEvents()
    data class SetQuizType(val quizType: String) : HomeScreenEvents()
}