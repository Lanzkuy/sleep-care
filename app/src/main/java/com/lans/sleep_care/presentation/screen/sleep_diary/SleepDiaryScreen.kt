package com.lans.sleep_care.presentation.screen.sleep_diary

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.lans.sleep_care.presentation.component.form.NumberDropDown
import com.lans.sleep_care.presentation.component.form.SleepDiary
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun SleepDiaryScreen(
    viewModel: SleepDiaryViewModel = hiltViewModel(),
    therapyId: String,
    navigateToLogbook: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }
    var isDropDownExpanded by remember { mutableStateOf(false) }
    var selectedWeek by remember { mutableIntStateOf(0) }
    val diaryExpandedStates = remember { mutableStateMapOf<String, Boolean>() }

    val selectedDates = state.sleepDiaries
        .filter { it.week == selectedWeek + 1 }
        .map { Pair(it.id, it.date) }

    val allAnswers = state.sleepDiaries
        .filter { it.week == selectedWeek + 1 }
        .mapNotNull { it.logbookAnswerList?.answers }

    val tempAnswers = remember { mutableStateListOf<Pair<Int, LogbookQuestionAnswer>>() }

    val mergedAnswers = allAnswers.map { answers ->
        answers.map { original ->
            tempAnswers.firstOrNull { it.second.answer.id == original.answer.id }?.second
                ?: original
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadSleepDiaries(therapyId.toInt())
    }

    LaunchedEffect(selectedWeek) {
        viewModel.loadSleepDiaries(therapyId = therapyId.toInt(), week = selectedWeek + 1)
    }

    LaunchedEffect(key1 = state.isCreated, key2 = state.isUpdated, key3 = state.error) {
        val isCreated = state.isCreated
        val isUpdated = state.isUpdated
        val error = state.error

        if (isCreated) {
            Toast.makeText(context, "Catatan tidur berhasil disimpan", Toast.LENGTH_SHORT).show()
            state.isCreated = false
            tempAnswers.clear()
        }

        if (isUpdated) {
            Toast.makeText(context, "Catatan tidur berhasil diperbaharui", Toast.LENGTH_SHORT)
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
                        navigateToLogbook.invoke()
                    }
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(R.string.sleep_diary),
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
                    .height(Dimens.dp8)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(Dimens.dp8),
                    text = stringResource(R.string.week_counter, selectedWeek + 1),
                    fontSize = Dimens.sp16,
                    fontWeight = FontWeight.SemiBold
                )
                NumberDropDown(
                    isExpanded = isDropDownExpanded,
                    selectedPosition = selectedWeek,
                    numbers = if (state.sleepDiaries.isNotEmpty()) {
                        state.sleepDiaries.map { it.week }.distinct().sorted()
                    } else {
                        listOf(1)
                    },
                    onExpandToggle = { isDropDownExpanded = !isDropDownExpanded },
                    onNumberSelected = {
                        if (state.sleepDiaries.isNotEmpty()) {
                            selectedWeek = it
                        }
                    }
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp8)
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
                SleepDiary(
                    dateWithIds = selectedDates,
                    questions = state.questions,
                    answers = mergedAnswers,
                    expandedStates = diaryExpandedStates,
                    onAnswerChanged = { recordId, questionAnswer ->
                        val originalAnswer = allAnswers
                            .flatten()
                            .firstOrNull { it.answer.id == questionAnswer.answer.id }

                        if (originalAnswer != null) {
                            val currentTemp = tempAnswers.firstOrNull {
                                it.second.answer.id == questionAnswer.answer.id
                            }

                            if (questionAnswer.answer != originalAnswer.answer) {
                                if (currentTemp != null) {
                                    val index = tempAnswers.indexOf(currentTemp)
                                    tempAnswers[index] = Pair(recordId, questionAnswer)
                                } else {
                                    tempAnswers.add(Pair(recordId, questionAnswer))
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
                        SleepDiaryUIEvent.SaveButtonClicked(
                            therapyId = therapyId.toInt(),
                            recordAnswers = tempAnswers.groupBy(
                                keySelector = { it.first },
                                valueTransform = { it.second }
                            )
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

