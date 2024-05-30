package com.example.quizappmvvm.domain.usecases

import com.example.quizappmvvm.common.Resource
import com.example.quizappmvvm.domain.model.Quiz
import com.example.quizappmvvm.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetQuizzesUseCase @Inject constructor(private val quizRepository: QuizRepository) {

    operator fun invoke(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Flow<Resource<List<Quiz>>> = flow {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(
                    data = quizRepository.getQuizzes(
                        amount,
                        category,
                        difficulty,
                        type
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}