package com.lans.sleep_care.presentation.screen.history_detail

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.misc.HistoryTherapyDetail
import com.lans.sleep_care.presentation.component.misc.HistoryTherapySession
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun HistoryDetailScreen(
    viewModel: HistoryDetailViewModel = hiltViewModel(),
    therapyId: Int,
    psychologistId: Int,
    period: String,
    totalPrice: String,
    navigateToHistory: () -> Unit,
    navigateToLogbook: () -> Unit,
    navigateToIdentifyValue: () -> Unit
) {
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.loadPsychologist(psychologistId)
        viewModel.loadTherapySchedules(therapyId)
    }

    LaunchedEffect(key1 = state.error) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.dp24),
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
                    navigateToHistory.invoke()
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.history_detail),
                textAlign = TextAlign.Center,
                fontSize = Dimens.sp24,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .size(Dimens.dp50)
            )
        }
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
            HorizontalDivider(thickness = Dimens.dp0p5)
            HistoryTherapyDetail(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.dp24),
                period = period,
                totalPrice = totalPrice,
                psychologist = state.psychologist
            )
            HorizontalDivider(thickness = Dimens.dp0p5)
            HistoryTherapySession(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.dp24,
                        vertical = Dimens.dp16
                    ),
                schedules = state.schedules,
                onIdentifyValueClick = {
                    navigateToIdentifyValue.invoke()
                },
                onItemClick = {
                    navigateToLogbook.invoke()
                }
            )
        }
    }
}