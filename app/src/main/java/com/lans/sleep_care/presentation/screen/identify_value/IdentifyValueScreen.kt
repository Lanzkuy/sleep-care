package com.lans.sleep_care.presentation.screen.identify_value

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.items.ValueAreaItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun IdentifyValueScreen(
    viewModel: IdentifyValueViewModel = hiltViewModel(),
    therapyId: String,
    navigateToMyTherapy: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    val tempAnswers = remember { mutableStateListOf<LogbookQuestionAnswer>() }

    val mergedAnswers = state.answers.map { original ->
        tempAnswers.firstOrNull {
            it.questionId == original.questionId &&
                    it.answer.note == original.answer.note
        } ?: original
    }

    LaunchedEffect(Unit) {
        viewModel.loadQuestions(therapyId.toInt())
    }

    LaunchedEffect(key1 = state.isCreated, key2 = state.isUpdated, key3 = state.error) {
        val isCreated = state.isCreated
        val isUpdated = state.isUpdated
        val error = state.error

        if (isCreated) {
            Toast.makeText(
                context,
                "Identifikasi nilai pribadi berhasil disimpan",
                Toast.LENGTH_SHORT
            ).show()
            state.isCreated = false
            tempAnswers.clear()
        }

        if (isUpdated) {
            Toast.makeText(
                context,
                "Identifikasi nilai pribadi berhasil diperbaharui",
                Toast.LENGTH_SHORT
            )
                .show()
            state.isUpdated = false
            tempAnswers.clear()
        }

        if (error.isNotBlank()) {
            showAlert = Pair(true, error)
            state.error = ""
        }
    }

    if (showAlert.first) {
        ValidationAlert(
            title = stringResource(R.string.alert_error_title),
            message = showAlert.second,
            onDismiss = {
                showAlert = showAlert.copy(first = false)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(Dimens.dp24)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.dp12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedIconButton(
                    modifier = Modifier
                        .size(Dimens.dp50),
                    color = White,
                    tint = Black,
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    shape = Rounded,
                    onClick = {
                        navigateToMyTherapy.invoke()
                    }
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(R.string.identify_value),
                    textAlign = TextAlign.Center,
                    fontSize = Dimens.sp24,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier
                        .size(Dimens.dp50)
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp16)
            )
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(Dimens.dp32)
                            .align(Alignment.Center),
                        color = Primary
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.dp16)
                ) {
                    items(state.areas) { area ->
                        val answers = mergedAnswers.filter { it.answer.note == area }
                        ValueAreaItem(
                            areaName = area,
                            questions = state.questions.sortedBy { it.type },
                            answers = answers,
                            onDataChange = { questionAnswer ->
                                val originalAnswer = state.answers.firstOrNull {
                                    it.questionId == questionAnswer.questionId &&
                                            it.answer.note == questionAnswer.answer.note
                                }

                                val currentTemp = tempAnswers.firstOrNull {
                                    it.questionId == questionAnswer.questionId &&
                                            it.answer.note == questionAnswer.answer.note
                                }

                                if (questionAnswer.answer.id == 0) {
                                    if (currentTemp == null) {
                                        tempAnswers.add(questionAnswer)
                                    } else {
                                        val index = tempAnswers.indexOf(currentTemp)
                                        tempAnswers[index] = questionAnswer
                                    }
                                } else if (originalAnswer != null) {
                                    if (questionAnswer.answer != originalAnswer.answer) {
                                        if (currentTemp != null) {
                                            val index = tempAnswers.indexOf(currentTemp)
                                            tempAnswers[index] = questionAnswer
                                        } else {
                                            tempAnswers.add(questionAnswer)
                                        }
                                    } else {
                                        if (currentTemp != null) {
                                            tempAnswers.remove(currentTemp)
                                        }
                                    }
                                }
                            }
                        )
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .height(Dimens.dp16)
                        )
                    }
                }
            }
        }

        if (tempAnswers.isNotEmpty()) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                containerColor = Primary,
                contentColor = White,
                shape = Rounded,
                onClick = {
                    viewModel.onEvent(
                        IdentifyValueUIEvent.SaveButtonClicked(
                            therapyId = therapyId.toInt(),
                            recordAnswers = tempAnswers
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.icon)
                )
            }
        }
    }
}