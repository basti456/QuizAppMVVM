package com.example.quizappmvvm.presentation.home

data class HomeScreenState(
    val noOfQuiz:Int = 10,
    val category:String = "General Knowledge",
    val difficulty:String = "Easy",
    val type:String = "Multiple Choice"
)
