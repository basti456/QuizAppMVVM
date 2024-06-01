package com.example.quizappmvvm.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappmvvm.common.Resource
import com.example.quizappmvvm.domain.model.Quiz
import com.example.quizappmvvm.domain.usecases.GetQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val getQuizzesUseCase: GetQuizzesUseCase) :
    ViewModel() {

    private val _quizList = MutableStateFlow(QuizScreenState())
    val quizList = _quizList
    fun onEvent(event: QuizScreenEvents) {
        when (event) {
            is QuizScreenEvents.GetQuizzes -> {
                getQuizzes(
                    event.numOfQuizzes,
                    event.category,
                    event.difficulty,
                    event.type
                )
            }
        }
    }

    private fun getListOfQuizState(data: List<Quiz>?): List<QuizState> {
        val listOfQuizState = mutableListOf<QuizState>()
        for (quiz in data!!) {
            val shuffledOptions = mutableListOf<String>().apply {
                add(quiz.correct_answer)
                addAll(quiz.incorrect_answers)
                shuffle()
            }
            listOfQuizState.add(QuizState(quiz, shuffledOptions))
        }
        return listOfQuizState
    }

    private fun getQuizzes(amount: Int, category: Int, difficulty: String, type: String) {
        viewModelScope.launch {
            getQuizzesUseCase(amount, category, difficulty, type).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _quizList.value = QuizScreenState(isLoading = true)
                    }

                    is Resource.Success -> {
                        val listOfQuizState: List<QuizState> = getListOfQuizState(resource.data)
                        _quizList.value =
                            QuizScreenState(isLoading = false, quizState = listOfQuizState)
                    }

                    is Resource.Error -> {
                        _quizList.value =
                            QuizScreenState(isLoading = false, error = resource.message.toString())
                    }
                }
            }
        }
    }
}