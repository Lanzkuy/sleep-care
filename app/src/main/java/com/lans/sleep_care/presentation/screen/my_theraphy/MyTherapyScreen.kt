package com.lans.sleep_care.presentation.screen.my_theraphy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.NoteDialog
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.items.ScheduleItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White
import com.lans.sleep_care.utils.generateWeeklyRanges
import com.lans.sleep_care.utils.parseToDate

@Composable
fun MyTherapyScreen(
    viewModel: MyTherapyViewModel = hiltViewModel(),
    isTherapyInProgress: Boolean,
    navigateToHome: () -> Unit,
    navigateToPsychologist: () -> Unit,
    navigateToChat: (therapyId: String, psychologistName: String) -> Unit,
    navigateToIdentifyValue: (therapyId: String, isReadOnly: Boolean) -> Unit,
    navigateToLogbook: (therapyId: String, week: String, startDate: String, endDate: String, isReadOnly: Boolean) -> Unit
) {
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }
    var showNoteDialog by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.loadActiveTherapy()
    }

    LaunchedEffect(key1 = state.schedules, key2 = state.error) {
        val error = state.error

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

    if (showNoteDialog.first) {
        NoteDialog(
            note = showNoteDialog.second,
            onClose = {
                showNoteDialog = showNoteDialog.copy(first = false)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimens.dp24,
                    top = Dimens.dp24,
                    end = Dimens.dp24
                ),
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
                    navigateToHome.invoke()
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.mytherapy),
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
        if (state.isTherapyLoading) {
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
        } else if (isTherapyInProgress && state.therapy != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.dp24
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(Dimens.dp48)
                        .padding()
                        .clip(CircleShape),
                    model = state.psychologist.user.avatar,
                    placeholder = painterResource(R.drawable.img_user_placeholder),
                    error = painterResource(R.drawable.img_user_placeholder),
                    contentDescription = stringResource(R.string.image),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .padding(start = Dimens.dp8)
                        .weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = Dimens.dp4),
                        text = state.psychologist.user.name,
                        color = DarkGray,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = Dimens.sp18,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = Dimens.dp4),
                        text = "${parseToDate(state.therapy!!.startDate)} - ${parseToDate(state.therapy!!.endDate)}",
                        color = DarkGray,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                ElevatedIconButton(
                    modifier = Modifier
                        .size(Dimens.dp48),
                    color = White,
                    tint = Black,
                    icon = painterResource(R.drawable.ic_message),
                    shape = Rounded,
                    onClick = {
                        navigateToChat.invoke(
                            state.therapy?.id.toString(),
                            state.psychologist.user.name
                        )
                    }
                )
            }
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp24)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp36)
                    .padding(horizontal = Dimens.dp24),
                shape = RoundedLarge,
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Primary,
                        contentColor = White
                    ),
                onClick = {
                    navigateToIdentifyValue.invoke(state.therapy!!.id.toString(), false)
                }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = stringResource(R.string.identify_value)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp16)
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        horizontal = Dimens.dp24
                    ),
                border = BorderStroke(Dimens.dp1, Gray),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = White
                ),
                shape = RoundedCornerShape(Dimens.dp20)
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.dp16,
                            vertical = Dimens.dp12
                        ),
                    text = stringResource(R.string.therapy_schedule),
                    fontSize = Dimens.sp20,
                    fontWeight = FontWeight.SemiBold
                )
                if (state.isScheduleLoading) {
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
                            .padding(
                                horizontal = Dimens.dp16
                            )
                    ) {
                        itemsIndexed(state.schedules) { index, schedule ->
                            val dateRange = generateWeeklyRanges(
                                startDate = state.therapy!!.startDate,
                                endDate = state.therapy!!.endDate
                            )

                            ScheduleItem(
                                date = schedule.date.ifEmpty { "Belum ditentukan" },
                                topic = schedule.title.ifEmpty { "Belum ditentukan" },
                                link = schedule.link.ifEmpty { "Belum ditentukan" },
                                note = schedule.note,
                                onLogbookClick = {
                                    navigateToLogbook.invoke(
                                        schedule.therapyId.toString(),
                                        (index + 1).toString(),
                                        dateRange[index].first,
                                        dateRange[index].second,
                                        false
                                    )
                                },
                                onNoteClick = {
                                    showNoteDialog = showNoteDialog.copy(
                                        first = true,
                                        second = schedule.note
                                    )
                                }
                            )
                        }
                    }
                }
            }
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = Dimens.dp24),
//                verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
//            ) {
//                items(buttonItems) { (icon, name, action) ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        OutlinedIconButton(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            icon = painterResource(icon),
//                            name = stringResource(name),
//                            onClick = { action.invoke() }
//                        )
//
//                        if (name == R.string.chat_psychologist && state.unreadMessage > 0) {
//                            Box(
//                                modifier = Modifier
//                                    .padding(end = Dimens.dp12)
//                                    .background(Danger, shape = Rounded)
//                                    .size(Dimens.dp24)
//                                    .align(Alignment.CenterEnd),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = state.unreadMessage.toString(),
//                                    color = White,
//                                    style = MaterialTheme.typography.labelMedium
//                                )
//                            }
//                        }
//                    }
//                }
//            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .width(Dimens.dp300)
                        .height(Dimens.dp240)
                        .padding(end = Dimens.dp16),
                    painter = painterResource(R.drawable.img_illustration_schedule),
                    contentDescription = stringResource(R.string.image),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = stringResource(R.string.no_therapy_session),
                    textAlign = TextAlign.Center,
                    color = DarkGray,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.dp16)
                )
                Button(
                    modifier = Modifier
                        .width(Dimens.dp300)
                        .height(Dimens.dp48),
                    shape = Rounded,
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = Dimens.dp4
                    ),
                    onClick = {
                        navigateToPsychologist.invoke()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.order_therapy).uppercase(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}