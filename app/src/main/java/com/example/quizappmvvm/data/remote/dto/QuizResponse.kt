package com.example.quizappmvvm.data.remote.dto

import com.example.quizappmvvm.domain.model.Quiz

data class QuizResponse(
    val response_code: Int,
    val results: List<Quiz>
)