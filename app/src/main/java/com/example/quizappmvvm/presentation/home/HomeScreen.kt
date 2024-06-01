package com.example.quizappmvvm.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.quizappmvvm.R
import com.example.quizappmvvm.presentation.common.AppDropdownMenu
import com.example.quizappmvvm.presentation.common.ButtonBox
import com.example.quizappmvvm.presentation.home.component.HomeHeader
import com.example.quizappmvvm.presentation.nav_graph.Routes
import com.example.quizappmvvm.presentation.util.Constants
import com.example.quizappmvvm.presentation.util.Dimens.MediumPadding
import com.example.quizappmvvm.presentation.util.Dimens.MediumSpacerHeight

@Composable
fun HomeScreen(
    state: HomeScreenState,
    event: (HomeScreenEvents) -> Unit,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        HomeHeader()

        Spacer(modifier = Modifier.height(MediumSpacerHeight))
        AppDropdownMenu(
            menuName = "Number Of Questions:",
            Constants.numberAsString,
            text = state.noOfQuiz.toString(),
            onDropdownClick = { event(HomeScreenEvents.SetNumberOfQuizzes(it.toInt())) })

        Spacer(modifier = Modifier.height(MediumSpacerHeight))
        AppDropdownMenu(
            menuName = "Select Category:",
            Constants.categories,
            text = state.category,
            onDropdownClick = { event(HomeScreenEvents.SetQuizCategory(it)) })

        Spacer(modifier = Modifier.height(MediumSpacerHeight))
        AppDropdownMenu(
            menuName = "Select Difficulty:",
            Constants.difficulty,
            text = state.difficulty,
            onDropdownClick = { event(HomeScreenEvents.SetQuizDifficulty(it)) })

        Spacer(modifier = Modifier.height(MediumSpacerHeight))
        AppDropdownMenu(
            menuName = "Select Type",
            Constants.type,
            text = state.type,
            onDropdownClick = { event(HomeScreenEvents.SetQuizType(it)) })

        Spacer(modifier = Modifier.height(MediumSpacerHeight))
        ButtonBox(text = "Generate Quiz", padding = MediumPadding, textColor = colorResource(id = R.color.black)) {
            navController.navigate(
                Routes.QuizScreen.passQuizParams(
                    state.noOfQuiz,
                    state.category,
                    state.difficulty,
                    state.type
                )
            )
        }
    }
}