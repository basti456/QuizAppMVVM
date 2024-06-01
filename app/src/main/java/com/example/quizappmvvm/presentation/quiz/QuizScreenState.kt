package com.example.quizappmvvm.presentation.quiz

import com.example.quizappmvvm.domain.model.Quiz

data class QuizScreenState(
    val isLoading: Boolean = false,
    val quizState: List<QuizState> = emptyList(),
    val error: String = ""
)

data class QuizState(
    val quiz: Quiz? = null,
    val shuffledOptions: List<String> = emptyList(),
    val selectedOption: Int? = -1
)
