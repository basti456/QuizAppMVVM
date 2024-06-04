package com.example.quizappmvvm.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizappmvvm.presentation.home.HomeScreen
import com.example.quizappmvvm.presentation.home.HomeScreenViewModel
import com.example.quizappmvvm.presentation.quiz.QuizScreen
import com.example.quizappmvvm.presentation.quiz.QuizViewModel
import com.example.quizappmvvm.presentation.score.ScoreScreen

@Composable
fun SetNavGraph() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            val viewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val state by viewModel.homeState.collectAsState()
            HomeScreen(
                state = state,
                event = viewModel::onEvent,
                navController = navController
            )
        }
        composable(
            route = Routes.QuizScreen.route,
            arguments = listOf(
                navArgument(ARG_KEY_QUIZ_NUMBER) { type = NavType.IntType },
                navArgument(ARG_KEY_QUIZ_CATEGORY) { type = NavType.StringType },
                navArgument(ARG_KEY_QUIZ_DIFFICULTY) { type = NavType.StringType },
                navArgument(ARG_KEY_QUIZ_TYPE) { type = NavType.StringType }
            )
        ) {
            val noOfQuizzes = it.arguments?.getInt(ARG_KEY_QUIZ_NUMBER)
            val quizCategory = it.arguments?.getString(ARG_KEY_QUIZ_CATEGORY)
            val quizDifficulty = it.arguments?.getString(ARG_KEY_QUIZ_DIFFICULTY)
            val quizType = it.arguments?.getString(ARG_KEY_QUIZ_TYPE)
            val viewModel: QuizViewModel = hiltViewModel<QuizViewModel>()
            val state by viewModel.quizList.collectAsState()
            QuizScreen(
                noOfQuiz = noOfQuizzes!!,
                quizCategory = quizCategory!!,
                quizDifficulty = quizDifficulty!!,
                quizType = quizType!!,
                event = viewModel::onEvent,
                state = state,
                navController = navController
            )
        }

        composable(
            route = Routes.ScoreScreen.route,
            arguments = listOf(
                navArgument(NOQ_KEY) { type = NavType.IntType },
                navArgument(CORRECT_ANS_KEY) { type = NavType.IntType }
            )
        ) {
            val noOfQuestions = it.arguments?.getInt(NOQ_KEY)
            val noOfCorrectAns = it.arguments?.getInt(CORRECT_ANS_KEY)
            ScoreScreen(
                noOfQuestions = noOfQuestions!!,
                noOfCorrectAns = noOfCorrectAns!!,
                navController = navController
            )
        }
    }
}