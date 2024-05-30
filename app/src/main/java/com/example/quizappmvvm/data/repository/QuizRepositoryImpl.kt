package com.example.quizappmvvm.data.repository

import com.example.quizappmvvm.data.remote.QuizApi
import com.example.quizappmvvm.domain.model.Quiz
import com.example.quizappmvvm.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(private val quizApi: QuizApi) : QuizRepository {
    override suspend fun getQuizzes(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): List<Quiz> {
        return quizApi.getQuizzes(amount, category, difficulty, type).results
    }
}