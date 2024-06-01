package com.example.quizappmvvm.presentation.quiz.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.quizappmvvm.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.quizappmvvm.presentation.util.Dimens

@Composable
fun QuizInterface(onOptionSelected: (Int) -> Unit, qNumber: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.wrapContentHeight()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "$qNumber",
                    modifier = Modifier.weight(1f),
                    color = colorResource(id = R.color.blue_grey),
                    fontSize = Dimens.SmallTextSize
                )
                Text(
                    text = "Which insurance policy do you want", modifier = Modifier.weight(9f),
                    colorResource(id = R.color.blue_grey), fontSize = Dimens.SmallTextSize
                )
            }

            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            Column(modifier = Modifier.padding(horizontal = 15.dp)) {

                val options = listOf(
                    "A" to "Bajaj",
                    "B" to "Metlife",
                    "C" to "Canary",
                    "D" to "Bharat"
                )
                options.forEachIndexed { index, (optionNumber, optionText) ->
                    if (optionText.isNotEmpty()) {
                        QuizOption(
                            optionNumber = optionNumber,
                            options = optionText,
                            selected = false,
                            onOptionsClick = { onOptionSelected(index) },
                            onUnselectOption = {
                                onOptionSelected(-1)
                            })
                    }
                    Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
                }
            }
            Spacer(modifier = Modifier.height(Dimens.ExtraLargeSpacerHeight))
        }
    }
}