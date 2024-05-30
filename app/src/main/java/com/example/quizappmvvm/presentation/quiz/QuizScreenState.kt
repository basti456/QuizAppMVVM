package com.example.quizappmvvm.presentation.quiz

import com.example.quizappmvvm.domain.model.Quiz

data class QuizScreenState(
    val isLoading: Boolean = false,
    val data: List<Quiz>? = listOf(),
    val error: String = ""

)
