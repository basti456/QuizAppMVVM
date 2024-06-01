package com.example.quizappmvvm.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import com.example.quizappmvvm.presentation.common.QuizAppBar
import com.example.quizappmvvm.presentation.util.Dimens
import com.example.quizappmvvm.R
import com.example.quizappmvvm.presentation.common.ButtonBox
import com.example.quizappmvvm.presentation.quiz.component.QuizInterface
import com.example.quizappmvvm.presentation.util.Constants

@Composable
fun QuizScreen(
    noOfQuiz: Int,
    quizCategory: String,
    quizDifficulty: String,
    quizType: String,
    event: (QuizScreenEvents) -> Unit,
    state: QuizScreenState
) {

    LaunchedEffect(key1 = Unit) {
        event(
            QuizScreenEvents.GetQuizzes(
                numOfQuizzes = noOfQuiz,
                category = Constants.categoriesMap[quizCategory]!!,
                difficulty = quizDifficulty.lowercase(),
                type = if (quizType == "Multiple Choice") {
                    "multiple"
                } else {
                    "boolean"
                }
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        QuizAppBar(quizCategory = quizCategory) {

        }

        Column(
            modifier = Modifier
                .padding(Dimens.VerySmallPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Questions : $noOfQuiz", color = colorResource(id = R.color.blue_grey))
                Text(text = quizDifficulty, color = colorResource(id = R.color.blue_grey))
            }

            Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.VerySmallViewHeight)
                    .clip(
                        RoundedCornerShape(Dimens.MediumCornerRadius)
                    )
                    .background(colorResource(id = R.color.blue_grey))
            )

            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            QuizInterface(onOptionSelected = {}, qNumber = 1, modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.MediumPadding)
                    .navigationBarsPadding()
            ) {
                ButtonBox(
                    text = "Previous",
                    padding = Dimens.SmallPadding,
                    fraction = 0.43f,
                    fontSize = Dimens.SmallTextSize
                ) {

                }
                ButtonBox(
                    text = "Next",
                    padding = Dimens.SmallPadding,
                    borderColor = colorResource(id = R.color.orange),
                    containerColor = colorResource(id = R.color.dark_slate_blue),
                    fraction = 1f,
                    textColor = colorResource(id = R.color.white),
                    fontSize = Dimens.SmallTextSize
                ) {

                }
            }
        }
    }
}