package com.example.quizappmvvm.domain.di

import com.example.quizappmvvm.data.remote.QuizApi
import com.example.quizappmvvm.data.repository.QuizRepositoryImpl
import com.example.quizappmvvm.domain.repository.QuizRepository
import com.example.quizappmvvm.domain.usecases.GetQuizzesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    @Singleton
    fun provideGetQuizzesUseCases(quizRepository: QuizRepository): GetQuizzesUseCase {
        return GetQuizzesUseCase(quizRepository)
    }
}