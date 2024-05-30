package com.example.quizappmvvm.domain.repository

import com.example.quizappmvvm.domain.model.Quiz

interface QuizRepository {

    suspend fun getQuizzes(amount:Int,category:Int,difficulty:String,type:String):List<Quiz>
}