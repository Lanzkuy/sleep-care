package com.lans.sleep_care.presentation.screen.sleep_diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.data.DATA
import com.lans.sleep_care.domain.model.DiaryAnswer
import com.lans.sleep_care.domain.model.DiaryQuestion
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.form.NumberDropDown
import com.lans.sleep_care.presentation.component.form.SleepDiary
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White
import com.lans.sleep_care.utils.generateDateRange
import com.lans.sleep_care.utils.splitDatesByWeek

@Composable
fun SleepDiaryScreen(
    viewModel: SleepDiaryViewModel = hiltViewModel(),
    navigateToLogbook: () -> Unit
) {
    // Temporary
    val allDates = remember { generateDateRange("2025-04-01", "2025-04-30") }
    val weeks = remember { splitDatesByWeek(allDates) }
    val dayQuestions = listOf(
        DiaryQuestion(
            id = 1,
            text = "Apakah kamu tidur siang?",
            isYesNo = true,
            subQuestions = listOf(
                DiaryQuestion(id = 2, text = "Berapa lama? (dalam menit)", isYesNo = false),
                DiaryQuestion(id = 3, text = "Pukul berapa?", isYesNo = false)
            )
        ),
        DiaryQuestion(
            id = 4,
            text = "Apakah kamu mengonsumsi kafein setelah pukul 18.00?",
            isYesNo = true
        ),
        DiaryQuestion(
            id = 5,
            text = "Apakah kamu mengonsumsi alkohol setelah pukul 18.00?",
            isYesNo = true
        ),
        DiaryQuestion(
            id = 6,
            text = "Apakah kamu mengonsumsi nikotin setelah pukul 18.00?",
            isYesNo = true
        ),
        DiaryQuestion(id = 7, text = "Apakah kamu berolahraga?", isYesNo = true),
        DiaryQuestion(
            id = 8,
            text = "Apakah kamu mengonsumsi makanan berat atau snack setelah pukul 18.00?",
            isYesNo = true
        ),
        DiaryQuestion(
            id = 9,
            text = "Apakah kamu mengonsumsi obat tidur?",
            isYesNo = true,
            subQuestions = listOf(
                DiaryQuestion(id = 10, text = "Apa jenis obatnya?", isYesNo = false),
                DiaryQuestion(id = 11, text = "Berapa banyak?", isYesNo = false),
                DiaryQuestion(id = 12, text = "Pukul berapa kamu mengkonsumsi?", isYesNo = false),
            )
        ),
        DiaryQuestion(id = 13, text = "Apakah kamu mengantuk sepanjang hari?", isYesNo = true)
    )
    val nightQuestions = listOf(
        DiaryQuestion(
            id = 14,
            text = "Pukul berapa kamu mulai mematikan lampu untuk mulai tidur?",
            isYesNo = false
        ),
        DiaryQuestion(id = 15, text = "Pukul berapa kamu bangun tidur?", isYesNo = false),
        DiaryQuestion(id = 16, text = "Berapa total jam kamu tidur? (dalam jam)", isYesNo = false),
        DiaryQuestion(id = 17, text = "Berapa kali kamu terbangun di malam hari?", isYesNo = false),
        DiaryQuestion(id = 18, text = "Isilah skala kualitas tidurmu (1-5)", isYesNo = false),
        DiaryQuestion(id = 19, text = "Apakah kamu merasa tidurmu cukup?", isYesNo = true)
    )
    val localSavedAnswers = DATA.savedAnswers
    val tempAnswers = remember { mutableStateMapOf<Pair<String, Int>, DiaryAnswer>() }

    val state by viewModel.state
    var isExpanded by remember { mutableStateOf(false) }
    var selectedWeek by remember { mutableIntStateOf(0) }
    val selectedDates = weeks.getOrNull(selectedWeek) ?: emptyList()

    LaunchedEffect(Unit) {
        tempAnswers.putAll(localSavedAnswers)
    }

    Box(
        modifier = Modifier
            .background(White)
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
                    isExpanded = isExpanded,
                    selectedPosition = selectedWeek,
                    numbers = weeks.indices.map { it + 1 },
                    onExpandToggle = { isExpanded = !isExpanded },
                    onNumberSelected = { selectedWeek = it }
                )
            }
            SleepDiary(
                dates = selectedDates,
                questions = Pair(dayQuestions, nightQuestions),
                answers = (localSavedAnswers + tempAnswers).values.toList(),
                onAnswerChanged = { date, questionId, newAnswer ->
                    val key = date to questionId
                    val allQuestions =
                        (dayQuestions + nightQuestions).flatMap { listOf(it) + it.subQuestions }
                    val question = allQuestions.find { it.id == questionId }

                    if (question != null) {
                        if (question.subQuestions.isNotEmpty()) {
                            val updatedSubAnswers = question.subQuestions.map { subQuestion ->
                                val subKey = date to subQuestion.id
                                tempAnswers[subKey] ?: DiaryAnswer(
                                    date = date,
                                    questionId = subQuestion.id,
                                    value = "",
                                    subAnswers = emptyList()
                                )
                            }

                            val newDiaryAnswer = DiaryAnswer(
                                date = date,
                                questionId = questionId,
                                value = newAnswer,
                                subAnswers = updatedSubAnswers
                            )
                            tempAnswers[key] = newDiaryAnswer
                        } else {
                            val newDiaryAnswer = DiaryAnswer(
                                date = date,
                                questionId = questionId,
                                value = newAnswer,
                                subAnswers = emptyList()
                            )
                            tempAnswers[key] = newDiaryAnswer
                        }
                    }
                }
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            containerColor = Primary,
            contentColor = White,
            shape = Rounded,
            onClick = {
                viewModel.saveAnswers(tempAnswers)
                tempAnswers.clear()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = stringResource(R.string.icon)
            )
        }
    }
}

