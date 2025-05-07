package com.lans.sleep_care.presentation.screen.my_theraphy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.NoteDialog
import com.lans.sleep_care.presentation.component.button.OutlinedIconButton
import com.lans.sleep_care.presentation.component.items.ScheduleItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun MyTherapyScreen(
    viewModel: MyTherapyViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToChat: () -> Unit,
    navigateToLogbook: () -> Unit
) {
    val buttonItems = listOf(
        Triple(R.drawable.ic_message, R.string.chat_psychologist, navigateToChat),
        Triple(R.drawable.ic_book, R.string.therapy_note, navigateToLogbook)
    )
    val scheduleList = listOf(
        TherapySchedule(
            "24/08/2025",
            "Topik 1",
            "https://meet.com/jady7",
            "tes123"
        ),
        TherapySchedule(
            "31/08/2025",
            "Topik 2",
            "https://meet.com/jay8h",
            ""
        )
    )
    val state by viewModel.state
    var showNoteDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        if(showNoteDialog) {
            NoteDialog(
                note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                onClose = {
                    showNoteDialog = false
                }
            )
        }

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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.dp24),
            verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
        ) {
            items(buttonItems) { (icon, name, action) ->
                OutlinedIconButton(
                    modifier = Modifier.fillMaxWidth(),
                    icon = painterResource(icon),
                    name = stringResource(name),
                    onClick = { action.invoke() }
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp16)
        )
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.dp24
                ),
            border = BorderStroke(Dimens.dp1, DarkGray),
            colors = CardDefaults.outlinedCardColors(
                containerColor = White
            ),
            shape = RoundedCornerShape(Dimens.dp20)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(Dimens.dp16)
            ) {
                item {
                    Text(
                        modifier = Modifier
                            .padding(bottom = Dimens.dp12),
                        text = stringResource(R.string.therapy_schedule),
                        fontSize = Dimens.sp20,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                items(scheduleList) { schedule ->
                    ScheduleItem(
                        date = schedule.date,
                        topic = schedule.topic,
                        link = schedule.link,
                        note = schedule.note,
                        onNoteClick = {
                            showNoteDialog = true
                        }
                    )
                }
            }
        }
    }
}

data class TherapySchedule(
    val date: String,
    val topic: String,
    val link: String,
    val note: String
)