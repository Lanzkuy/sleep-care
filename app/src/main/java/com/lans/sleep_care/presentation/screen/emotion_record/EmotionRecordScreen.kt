package com.lans.sleep_care.presentation.screen.emotion_record

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.lans.sleep_care.presentation.component.dialog.EmotionRecordDialog
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.items.EmotionRecordItem
import com.lans.sleep_care.presentation.screen.thought_record.ThoughtRecordUIEvent
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun EmotionRecordScreen(
    viewModel: EmotionRecordViewModel = hiltViewModel(),
    therapyId: String,
    navigateToLogbook: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }
    var showDialog by remember {
        mutableStateOf(
            Pair<Boolean, List<LogbookQuestionAnswer>>(
                false,
                emptyList()
            )
        )
    }

    val groupedAnswers: List<List<LogbookQuestionAnswer>> = run {
        if (state.answers.isEmpty()) emptyList()
        else {
            val firstQuestionId = state.answers.first().questionId
            state.answers.fold(
                mutableListOf<MutableList<LogbookQuestionAnswer>>()
            ) { groupedAnswers, answer ->
                if (groupedAnswers.isEmpty() || answer.questionId == firstQuestionId) {
                    groupedAnswers.add(mutableListOf())
                }
                groupedAnswers.last().add(answer)
                groupedAnswers
            }.sortedBy { group ->
                val date = group.find { it.answer.type == "date" }?.answer?.answer ?: "1970-01-01"
                val time = group.find { it.answer.type == "time" }?.answer?.answer ?: "00:00"
                "$date $time"
            }
        }
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
                "Catatan emosi berhasil disimpan",
                Toast.LENGTH_SHORT
            ).show()
            state.isCreated = false
        }

        if (isUpdated) {
            Toast.makeText(
                context,
                "Catatan emosi berhasil diperbaharui",
                Toast.LENGTH_SHORT
            )
                .show()
            state.isUpdated = false
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
                    text = stringResource(R.string.emotion_record),
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
                    items(groupedAnswers.size) { index ->
                        EmotionRecordItem(
                            answers = groupedAnswers[index],
                            onClick = {
                                showDialog = showDialog.copy(
                                    first = true,
                                    second = groupedAnswers[index]
                                )
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
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            containerColor = Primary,
            contentColor = White,
            shape = Rounded,
            onClick = {
                showDialog = showDialog.copy(
                    first = true,
                    second = emptyList()
                )
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.icon)
            )
        }
        if (showDialog.first) {
            EmotionRecordDialog(
                emotions = state.emotions,
                questions = state.questions,
                answers = showDialog.second,
                onDismiss = {
                    showDialog = showDialog.copy(first = false)
                },
                onSave = { isUpdate, records ->
                    viewModel.onEvent(
                        ThoughtRecordUIEvent.SaveButtonClicked(
                            therapyId = therapyId.toInt(),
                            isUpdate = isUpdate,
                            recordAnswers = records
                        )
                    )
                    showDialog = showDialog.copy(first = false)
                }
            )
        }
    }
}
