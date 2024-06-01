package com.example.quizappmvvm.presentation.quiz

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.example.quizappmvvm.R
import com.example.quizappmvvm.presentation.common.ButtonBox
import com.example.quizappmvvm.presentation.common.QuizAppBar
import com.example.quizappmvvm.presentation.quiz.component.QuizInterface
import com.example.quizappmvvm.presentation.quiz.component.ShimmerEffectQuizInterface
import com.example.quizappmvvm.presentation.util.Constants
import com.example.quizappmvvm.presentation.util.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizScreen(
    noOfQuiz: Int,
    quizCategory: String,
    quizDifficulty: String,
    quizType: String,
    event: (QuizScreenEvents) -> Unit,
    state: QuizScreenState
) {

    val context = LocalContext.current
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

            if (state.isLoading) {
                ShimmerEffectQuizInterface()
            } else if (state.quizState.isNotEmpty()) {
                val pagerState = rememberPagerState {
                    state.quizState.size
                }
                HorizontalPager(state = pagerState) { index ->
                    QuizInterface(
                        onOptionSelected = {},
                        qNumber = index + 1,
                        modifier = Modifier.weight(1f),
                        quizState = state.quizState[index]
                    )
                }
                val buttonText by remember {
                    derivedStateOf {
                        when (pagerState.currentPage) {
                            0 -> {
                                listOf("", "Next")
                            }

                            state.quizState.size - 1 -> {
                                listOf("Previous", "Submit")
                            }

                            else -> {
                                listOf("Previous", "Next")
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.MediumPadding)
                        .navigationBarsPadding()
                ) {
                    val scope = rememberCoroutineScope()
                    if (buttonText[0] != "") {
                        ButtonBox(
                            text = "Previous",
                            padding = Dimens.SmallPadding,
                            fraction = 0.43f,
                            fontSize = Dimens.SmallTextSize,
                            textColor = colorResource(id = R.color.black)
                        ) {

                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        }
                    }
                    if (buttonText[1] != "") {
                        ButtonBox(
                            text = buttonText[1],
                            padding = Dimens.SmallPadding,
                            borderColor = colorResource(id = R.color.orange),
                            containerColor = colorResource(id = R.color.dark_slate_blue),
                            fraction = 1f,
                            textColor = colorResource(id = R.color.white),
                            fontSize = Dimens.SmallTextSize
                        ) {
                            if (pagerState.currentPage == state.quizState.size - 1) {
                                //navigate to score screen
                            } else {
                                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                            }

                        }
                    }
                }

            } else if (state.error != "") {
                Toast.makeText(context, "Something went wrong ${state.error}", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }
}