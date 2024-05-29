package com.example.quizappmvvm.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(): ViewModel() {


    private val _homeState = MutableStateFlow(HomeScreenState())
    val homeState = _homeState

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.SetNumberOfQuizzes -> {
                _homeState.value = homeState.value.copy(noOfQuiz = event.numberOfQuizzes)
            }

            is HomeScreenEvents.SetQuizCategory -> {
                _homeState.value = homeState.value.copy(category = event.quizCategory)
            }

            is HomeScreenEvents.SetQuizDifficulty -> {
                _homeState.value = homeState.value.copy(difficulty = event.quizDifficulty)
            }

            is HomeScreenEvents.SetQuizType -> {
                _homeState.value = homeState.value.copy(type = event.quizType)
            }
        }
    }
}