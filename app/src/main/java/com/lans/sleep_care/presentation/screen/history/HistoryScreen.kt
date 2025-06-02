package com.lans.sleep_care.presentation.screen.history

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.items.HistoryItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White
import com.lans.sleep_care.utils.parseToDate

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToHistoryDetail: (
        therapyId: String,
        psychologistId: String,
        period: String,
        totalPrice: String
    ) -> Unit
) {
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.loadTherapies()
    }

    LaunchedEffect(key1 = state.therapies, key2 = state.error) {
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
            .padding(Dimens.dp24)
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
                    navigateToHome.invoke()
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.history),
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimens.dp32)
                        .align(Alignment.Center),
                    color = Primary
                )
            } else if (state.therapies.isEmpty()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(Dimens.dp160),
                        painter = painterResource(R.drawable.img_illustration_nothing),
                        contentDescription = stringResource(R.string.image)
                    )
                    Text(
                        text = stringResource(R.string.nothing_here),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
                ) {
                    items(state.therapies) { therapy ->
                        val period =
                            "${parseToDate(therapy.startDate)} - ${parseToDate(therapy.endDate)}"
                        HistoryItem(
                            name = state.psychologists.find {
                                it.id == therapy.doctorId
                            }?.user?.name ?: "Doctor",
                            price = therapy.applicationFee + therapy.doctorFee,
                            date = period,
                            onClick = {
                                navigateToHistoryDetail.invoke(
                                    therapy.id.toString(),
                                    therapy.doctorId.toString(),
                                    period,
                                    (therapy.applicationFee + therapy.doctorFee).toString()
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}